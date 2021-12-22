package io.eliez.lab.log4jextra

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Main : CliktCommand(printHelpOnEmptyArgs = true) {

    private val logger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger("main")
    private val log4jLogger: Logger = LogManager.getLogger("main")

    private val uuid by argument(help = "Go to https://log4j-tester.trendmicro.com to get your JNDI snapshot")

    override fun run() {
        val tmURL = "\${jndi:ldap://log4j-tester.trendmicro.com:1389/$uuid}"

        logger.info("Using SLF4J: {}", tmURL)
        log4jLogger.info("Using Log4J API: {}", tmURL)
    }
}

fun main(args: Array<String>) = Main().main(args)
