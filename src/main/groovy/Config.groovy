class Config {

  // ----------------- LogBack --------------------------
  static LOG_HOME         = 'C:\\ficks\\logs'
  static LOG_NAME         = 'post_index'
  static LOG_NAME_SYSTEM  = 'post_index_system'

  // ----------------- Postgres --------------------------
  static DB_URL    = 'jdbc:postgresql://localhost:5432/ficks'
  static DB_USER   = 'ficks'
  static DB_PASS   = 'ficks'
  static DB_DRIVER = 'org.postgresql.Driver'

  // ----------------- Directory -------------------------
  static ROOT_DIR = 'C:\\ficks\\documents'

  // ----------------- Apache Solr -----------------------
  static SOLR_CORE = 'ficks'
}