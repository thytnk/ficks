//@GrabConfig(systemClassLoader=true)

//import static java.net.URLEncoder.encode

//@Grab('org.slf4j:slf4j-api:1.7.2')
//@Grab('ch.qos.logback:logback-classic:1.0.9')
import groovy.util.logging.*
import groovy.sql.Sql

@Slf4j
class IndexAgent {
    def sql

    def execute(options) {
        log.info "-------------------- start update index --------------------"
        
        def query = (options.f) ?
            "SELECT * FROM documents ORDER BY small_code" :
            "SELECT * FROM documents WHERE indexed = false ORDER BY small_code";

        sql = Sql.newInstance(Config.DB_URL, Config.DB_USER, Config.DB_PASS, Config.DB_DRIVER)
        sql.eachRow(query, {
            indexFileOf it 
            indexDataOf it
            updateStatusOf it
        });
        log.info "-------------------- complete update index --------------------"
    }

    def indexFileOf(doc) {
        def command = "java -jar -Dauto -Dc=${Config.SOLR_CORE} "
        command += "-Dparams=\"literal.id=${doc.document_id}"
        command += "&literal.doc_code=${doc.code}"
        command += "&literal.doc_category=${doc.category}"
        command += "&literal.doc_purpose=${doc.purpose}"
        command += "\" -Drecursive  post.jar ${Config.ROOT_DIR}\\${doc.code}"            
        log.info command

        Process p = command.execute()
        p.waitFor();
    }

    def indexDataOf(doc) {
        def command = "curl --request POST ${Config.FICKS_API_ROOT}/documents/index/${doc.code}"
        log.info command

        Process p = command.execute()
        p.waitFor();
    }

    def updateStatusOf(doc) {
        sql.execute '''
            UPDATE documents
               SET index_date = CURRENT_DATE
                 , indexed    = TRUE
             WHERE code = ?
            ''', [doc.code]
    }
}

def cli = new CliBuilder()
cli.f(longOpt:'force', '全てのインデックスを再作成する')
options = cli.parse(args)
new IndexAgent().execute(options)
