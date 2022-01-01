package io.eliez.lab.log4jextra

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.system.exitProcess

class Main {

    private val logger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger("main")
    private val log4jLogger: Logger = LogManager.getLogger("main")

    fun run(uuid: String) {
        val tmURL = "\${jndi:ldap://log4j-tester.trendmicro.com:1389/$uuid}"

        logger.info("Using SLF4J: {}", tmURL)
        log4jLogger.info("Using Log4J API: {}", tmURL)
    }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Invalid arguments.\nPass the JNDI snapshot as the only argument.")
        exitProcess(1)
    }
    Main().run(args[0])
}
