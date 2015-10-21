class Config {

  // ----------------- LogBack --------------------------
  static LOG_HOME         = 'D:\\ficks\\logs'
  static LOG_NAME         = 'post_index'
  static LOG_NAME_SYSTEM  = 'post_index_system'

  // ----------------- Postgres --------------------------
  static DB_URL    = 'jdbc:postgresql://localhost:5432/ficks_production'
  static DB_USER   = 'ficks'
  static DB_PASS   = 'ficks'
  static DB_DRIVER = 'org.postgresql.Driver'

  // ----------------- Directory -------------------------
  static ROOT_DIR = 'D:\\ficks\\documents'
  static IMG_DIR = 'D:\\ficks\\img'

  // ----------------- Apache Solr -----------------------
  static SOLR_CORE = 'ficks_production'

  // ----------------- FICKS API -----------------------
  static FICKS_API_ROOT = 'http://localhost/api'
}