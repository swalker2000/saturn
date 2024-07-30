package org.saturn.generator.messageconsructor.parser.strategy

import org.saturn.generator.usefuldata.UsefulData

/**
 * Message parsing strategy.
 */
interface ParseStrategy {

    /**
     * Check if the message is valid.
     */
    fun check(data : List<Int>) : CheckResult

    /**
     * Parse the message.
     */
    fun parse(data : List<Int>) : UsefulData

}