package org.saturn.generator.messageconsructor.parser

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Mirror
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Separator
import org.saturn.generator.messageconsructor.parser.strategy.CheckResult
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategy
import org.saturn.generator.messageconsructor.parser.strategy.length.LengthParseStrategy
import org.saturn.generator.messageconsructor.parser.strategy.separator.SeparatorParseStrategy
import org.saturn.generator.usefuldata.UsefulData

class Parser(private val parts: List<Part>) {

    private lateinit var parseStrategy: ParseStrategy
    init {
        if(parts.any { it is Separator })
        {
            parseStrategy = SeparatorParseStrategy(parts)
        }
        else if(parts.any{it is Length})
        {
            parseStrategy = LengthParseStrategy(parts)
        }
        else
            Exception("Format not support!")
    }

    fun check(data : List<Int>) : CheckResult
    {
        return parseStrategy.check(data)
    }

    fun parse(data : List<Int>) : UsefulData
    {
        return parseStrategy.parse(data)
    }
}