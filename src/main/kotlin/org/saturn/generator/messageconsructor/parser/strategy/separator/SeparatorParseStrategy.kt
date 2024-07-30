package org.saturn.generator.messageconsructor.parser.strategy.separator

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Mirror
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Prefix
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Separator
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Suffix
import org.saturn.generator.messageconsructor.data.variable.Address
import org.saturn.generator.messageconsructor.data.variable.Command
import org.saturn.generator.messageconsructor.data.variable.Data
import org.saturn.generator.messageconsructor.parser.strategy.CheckResult
import org.saturn.generator.messageconsructor.parser.strategy.CommonCheckItems
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategy
import org.saturn.generator.usefuldata.UsefulData

class SeparatorParseStrategy(private val parts: List<Part>) : ParseStrategy {


    private val partsNoSeparator = parts.filter { it !is Separator }
    private val mirror : Int = if(parts.any { it is Mirror }) parts.first { it is Mirror}.getBytesInMessage()[0] else -1
    private val separator : Int = parts.first { it is Separator}.getBytesInMessage()[0]

    private val commonCheckItems = CommonCheckItems(partsNoSeparator)

    override fun check(data: List<Int>): CheckResult {


        val checkList = listOf(commonCheckItems.checkItemsCount,  commonCheckItems.checkSuffix, commonCheckItems.checkPrefix, commonCheckItems.checkLength)
        val items = getItems(data)

        for( i in 0..checkList.lastIndex)
        {
            val checkResult = checkList[i].exam(items)
            if(!checkResult.success)
                return checkResult
        }
        return CheckResult(true)
    }

    override fun parse(messageData: List<Int>): UsefulData {
        val items = getItems(messageData)
        var data = listOf<Int>()
        var command = listOf<Int>()
        var address = listOf<Int>()
        var dataIndex = partsNoSeparator.indexOfFirst { it is Data }
        var commandIndex = partsNoSeparator.indexOfFirst { it is Command }
        var addressIndex = partsNoSeparator.indexOfFirst { it is Address }
        if(dataIndex>=0)
        {
            data = items[dataIndex]
        }
        if(commandIndex>=0)
        {
            command = items[commandIndex]
        }
        if(addressIndex>=0)
        {
            address = items[addressIndex]
        }
        return UsefulData(address, command, data)
    }

    private fun getItems(data: List<Int>) : List<List<Int>>
    {
        val items : MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
        (0..data.lastIndex).forEach { index->
            if(data[index]==separator && (index==0 || data[index-1]!=mirror))
            {
                items.add(mutableListOf())
            }
            else
            {
                items.last().add(data[index])
            }
        }
        return items
    }

}