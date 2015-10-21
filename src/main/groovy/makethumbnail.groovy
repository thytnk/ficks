//@GrabConfig(systemClassLoader=true)

//import static java.net.URLEncoder.encode

//@Grab('org.slf4j:slf4j-api:1.7.2')
//@Grab('ch.qos.logback:logback-classic:1.0.9')
import groovy.util.logging.*
import groovy.sql.Sql

@Slf4j
class ThumbnailAgent {
    def sql

    def execute(options) {
        log.info "-------------------- start update index --------------------"
        sql = Sql.newInstance(Config.DB_URL, Config.DB_USER, Config.DB_PASS, Config.DB_DRIVER)
        sql.eachRow("SELECT * FROM documents", {
            makeThumbnail it 
        });
        log.info "-------------------- complete update index --------------------"
    }

    def makeThumbnail(doc) {
        def command = "MkThumbs.exe ${Config.ROOT_DIR}/${doc.code} ${Config.IMG_DIR}/${doc.code}.jpg"
        log.info command

        Process p = command.execute()
        p.waitFor();
    }

    def updateStatus(doc) {
        sql.execute '''
            UPDATE documents
               SET index_date = CURRENT_DATE
                 , indexed    = TRUE
             WHERE code = ?
            ''', [doc.code]
    }
}

def cli = new CliBuilder()
//cli.d(longOpt:'debug', '処理成功時にファイル移動しない')
options = cli.parse(args)
new ThumbnailAgent().execute(options)
