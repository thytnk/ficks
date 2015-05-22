import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import static ch.qos.logback.classic.Level.*

def LOG_HOME = Config.LOG_HOME
def PATTERN = '%d{yyyy-MM-dd HH:mm:ss} %-5level %logger - %msg%n'
def PATTERN_WARN = '%d{yyyy-MM-dd HH:mm:ss}\t%msg%n'

appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
    }
}

appender("ROLLING_SYSTEM", RollingFileAppender) {
	file = "${LOG_HOME}/${Config.LOG_NAME_SYSTEM}.log"
    encoder(PatternLayoutEncoder) {
        Pattern = PATTERN
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${LOG_HOME}/${Config.LOG_NAME_SYSTEM}_%d{yyyy-MM-dd}.log"
    }
}

appender("ROLLING_USER", RollingFileAppender) {
	file = "${LOG_HOME}/${Config.LOG_NAME}.log"
    encoder(PatternLayoutEncoder) {
        Pattern = PATTERN_WARN
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        //maxHistory = 30
        FileNamePattern = "${LOG_HOME}/${Config.LOG_NAME}_%d{yyyy-MM-dd}.log"
    }
}

root(DEBUG, ['CONSOLE'])

logger('CsvExporter', INFO, ['ROLLING_SYSTEM'])
logger('CsvExporter', WARN, ['ROLLING_USER'])
