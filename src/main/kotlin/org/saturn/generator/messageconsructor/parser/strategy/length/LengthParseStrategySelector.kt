package org.saturn.generator.messageconsructor.parser.strategy.length

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Separator
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategySelector

class LengthParseStrategySelector : ParseStrategySelector<LengthParseStrategy>() {
    override val positiveCaseList: List<ParseStrategySelector.Case> = listOf(
        ParseStrategySelector.Case{containsLength(it)}
    )
    override val negativeCaseList: List<ParseStrategySelector.Case> = listOf(
        ParseStrategySelector.Case{containsSeparator(it)}
    )

    override fun getParseStrategy(parts: List<Part>): LengthParseStrategy {
        return LengthParseStrategy(parts)
    }

    private fun containsSeparator(parts : List<Part>) :Boolean
    {
        return parts.any { it is Separator }
    }

    private fun containsLength(parts : List<Part>) :Boolean
    {
        return parts.any { it is Length }
    }
}