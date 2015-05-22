//@GrabConfig(systemClassLoader=true)

//import static java.net.URLEncoder.encode

//@Grab('org.slf4j:slf4j-api:1.7.2')
//@Grab('ch.qos.logback:logback-classic:1.0.9')
import groovy.util.logging.*
import groovy.sql.Sql

@Slf4j
class IndexAgent {
    def execute(options) {
        log.info "-------------------- start output TSV --------------------"
        def sql = Sql.newInstance(Config.DB_URL, Config.DB_USER, Config.DB_PASS, Config.DB_DRIVER)
        sql.eachRow("select * from documents where index_date is null", {
            def id = it.document_id
            def code = it.code

//            def command = "java -jar -Dauto -Dc=${Config.SOLR_CORE} -Dparams=\"literal.id=$code\" -Drecursive  post.jar ${Config.ROOT_DIR}\\$code"
            def command = "java -jar -Dauto -Dc=${Config.SOLR_CORE} "
            command += "-Dparams=\"literal.id=$id"
            command += "&literal.doc_code=$code"
            command += "&literal.doc_category=${it.category}"
            command += "&literal.doc_purpose=${it.purpose}"
//            command += "&literal.doc_dept_name=${encode(it.dept_name)}"
            command += "\" -Drecursive  post.jar ${Config.ROOT_DIR}\\$code"            
            log.info command

            Process p = command.execute()
            p.waitFor();
        });
        log.info "csv transformation completed."
    }
}

def cli = new CliBuilder()
//cli.d(longOpt:'debug', '処理成功時にファイル移動しない')
options = cli.parse(args)
new IndexAgent().execute(options)
