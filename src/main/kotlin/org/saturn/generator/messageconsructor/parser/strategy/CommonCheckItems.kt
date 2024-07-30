package org.saturn.generator.messageconsructor.parser.strategy

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Prefix
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Suffix

/**
 * A set of message verification functions.
 */
class CommonCheckItems(private val partsNoSeparator: List<Part>) {

    fun interface Examination
    {
        fun exam(items : List<List<Int>>) : CheckResult
    }

    val checkItemsCount = Examination{items->
        if(items.size==partsNoSeparator.size)
            CheckResult(true)
        else
            CheckResult(false, listOf("Items count error! Wait : ${partsNoSeparator.size}, have : ${items.size} "))
    }
    val checkPrefix= Examination{ items->

        val index = partsNoSeparator.indexOfFirst { it is Prefix }
        if(index<0)
        {
            CheckResult(true)
        }
        else if(items[index].size!=1)
        {
            CheckResult(false, listOf("Check prefix error! Item size not correct. Wait 1, have : ${items[index][0]}"))
        }
        else if(items[index][0]!= partsNoSeparator[index].getBytesInMessage()[0])
        {
            CheckResult(false, listOf("Check prefix error! Wait : ${partsNoSeparator[index].getBytesInMessage()[0]}, have : ${items[index][0]} "))
        }
        else
        {
            CheckResult(true)
        }
    }
    val checkSuffix= Examination{ items->
        val index = partsNoSeparator.indexOfFirst { it is Suffix }
        if(index<0)
        {
            CheckResult(true)
        }
        else if(items[index].size!=1)
        {
            CheckResult(false, listOf("Check suffix error! Item size not correct. Wait 1, have : ${items[index][0]}"))
        }
        else if(items[index][0]!= partsNoSeparator[index].getBytesInMessage()[0])
        {
            CheckResult(false, listOf("Check suffix error! Wait : ${partsNoSeparator[index].getBytesInMessage()[0]}, have : ${items[index][0]} "))
        }
        else
        {
            CheckResult(true)
        }
    }
    val checkLength= Examination{items->
        val index = partsNoSeparator.indexOfFirst { it is Length }
        if(index<0)
        {
            CheckResult(true)
        }
        else
        {
            val dependsOnList : List<Part> = (partsNoSeparator[index] as Length).dependsOn
            val dependsOnIndexList : List<Int> = dependsOnList.map { partsNoSeparator.indexOf(it) }
            val waitLength = dependsOnIndexList.sumOf { items[it].size }
            val getLength = items[index][0]
            if(waitLength==getLength)
                CheckResult(true)
            else
                CheckResult(false, listOf("Check length error! Wait : ${waitLength}, have : ${getLength} "))
        }
    }
}