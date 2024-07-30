package org.saturn.generator.messageconsructor.parser.strategy

import org.saturn.generator.usefuldata.UsefulData

interface ParseStrategy {

    /**
     * Соответствует ли данное сообщение протоколу.
     */
    fun check(data : List<Int>) : CheckResult

    /**
     * Получить полезные данные из сообщения.
     * прим. поля data, command
     */
    fun parse(data : List<Int>) : UsefulData

}