package org.saturn.generator.messageconsructor.parser

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Mirror
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Separator
import org.saturn.generator.messageconsructor.parser.strategy.CheckResult
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategy
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategySelector
import org.saturn.generator.messageconsructor.parser.strategy.length.LengthParseStrategy
import org.saturn.generator.messageconsructor.parser.strategy.length.LengthParseStrategySelector
import org.saturn.generator.messageconsructor.parser.strategy.separator.SeparatorParseStrategy
import org.saturn.generator.messageconsructor.parser.strategy.separator.SeparatorParseStrategySelector
import org.saturn.generator.usefuldata.UsefulData

class Parser(private val parts: List<Part>) {

    private lateinit var parseStrategy: ParseStrategy
    private val parseStrategySelectorList : List<ParseStrategySelector<out ParseStrategy>> = listOf(
        LengthParseStrategySelector(),
        SeparatorParseStrategySelector()
    )
    init {
        val validStrategies = parseStrategySelectorList.filter { it.check(parts) }
        if(validStrategies.isNotEmpty())
        {
            parseStrategy = validStrategies.first().getParseStrategy(parts)
        }
        else
            throw Exception("Format not support!")
    }

    /**
     * Check if the message is valid.
     */
    fun check(data : List<Int>) : CheckResult
    {
        return parseStrategy.check(data)
    }

    /**
     * Parse the message.
     */
    fun parse(data : List<Int>) : UsefulData
    {
        return parseStrategy.parse(data)
    }
}