//@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.POST

//@Grab(group = 'net.sf.opencsv', module = 'opencsv', version = '2.3')
import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.CSVWriter
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_ESCAPE_CHARACTER
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_SEPARATOR
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_QUOTE_CHARACTER

//@Grab('org.apache.httpcomponents:httpmime:4.3.2')
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.util.EntityUtils
import static org.apache.http.entity.ContentType.TEXT_PLAIN

def URL_ROOT  = 'http://localhost'
def CSV_FILE  = 'D:/ficks/import/ficks_document_20150805_link.csv'
def FILE_ROOT = 'D:/ficks/import/20150805_link/'
def http = new HTTPBuilder(URL_ROOT)

def csvReader = new CSVReader(new FileReader(new File(CSV_FILE)),
	DEFAULT_SEPARATOR,
	DEFAULT_ESCAPE_CHARACTER,
	DEFAULT_QUOTE_CHARACTER)

List<String[]> rows = csvReader.readAll()
def header = true
rows.each { row ->
	if (header) {
		header = false
	} else {
    	println(row[6])

    	def i = 0
    	http.request( POST, TEXT ) { req ->
    		uri.path = '/api/documents'
    		req.entity = MultipartEntityBuilder
    		.create()
    		.addTextBody('category'      , row[i],   TEXT_PLAIN)
    		.addTextBody('area'          , row[++i], TEXT_PLAIN)
    		.addTextBody('purpose'       , row[++i], TEXT_PLAIN)
    		.addTextBody('result'        , row[++i], TEXT_PLAIN)
    		.addTextBody('reason'        , row[++i], TEXT_PLAIN)
    		.addTextBody('publishDate'   , row[++i], TEXT_PLAIN)
    		.addTextBody('originalFileName', row[++i], TEXT_PLAIN.withCharset('UTF-8')) // ファイル名
            .addBinaryBody('file',  new File(FILE_ROOT + row[i])) // java.io.File
            .addTextBody('fileName',         row[++i], TEXT_PLAIN.withCharset('UTF-8')) // 登録ファイル名
            .addTextBody('deptName'      , row[++i], TEXT_PLAIN.withCharset('UTF-8'))
            .addTextBody('empNumber'     , row[++i], TEXT_PLAIN)
            .addTextBody('authorName'    , row[++i], TEXT_PLAIN.withCharset('UTF-8'))
            .addTextBody('customerName'  , row[++i], TEXT_PLAIN.withCharset('UTF-8'))
            .addTextBody('description'   , row[++i], TEXT_PLAIN.withCharset('UTF-8'))
            .addTextBody('printDirection', 'Vertical', TEXT_PLAIN)
            .build()

            response.success = { res, reader ->
            	println 'success'
            	def responseBytes = EntityUtils.toByteArray(res.getEntity())
            	def bis = new ByteArrayInputStream(responseBytes)
                // ... 用途に合わせてレスポンス処理
            }

            response.failure = { res, reader ->
            	println 'fail'
                // 失敗時
            }
        }
    }
}