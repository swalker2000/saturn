package org.saturn.generator.messageconsructor.parser.strategy

import org.saturn.generator.messageconsructor.data.Part
import java.util.concurrent.Callable

abstract class ParseStrategySelector<T : ParseStrategy>{

    fun interface Case
    {
        fun check(parts: List<Part>) : Boolean
    }

    protected abstract val positiveCaseList : List<Case>
    protected abstract val negativeCaseList : List<Case>

    fun check(parts: List<Part>)  :Boolean
    {
        return positiveCaseList.all { it.check(parts) } && negativeCaseList.none { it.check(parts) }
    }

    abstract fun getParseStrategy(parts: List<Part>) : T

}