package org.saturn.generator.messageconsructor.parser.strategy.separator

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Separator
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategySelector
import java.util.concurrent.Callable

class SeparatorParseStrategySelector : ParseStrategySelector<SeparatorParseStrategy>() {
    override val positiveCaseList: List<ParseStrategySelector.Case> = listOf(
        ParseStrategySelector.Case{containsSeparator(it)}
    )

    override val negativeCaseList: List<ParseStrategySelector.Case> = listOf()
    override fun getParseStrategy(parts: List<Part>): SeparatorParseStrategy {
        return SeparatorParseStrategy(parts)
    }

    private fun containsSeparator(parts : List<Part>) :Boolean
    {
        return parts.any { it is Separator }
    }
}