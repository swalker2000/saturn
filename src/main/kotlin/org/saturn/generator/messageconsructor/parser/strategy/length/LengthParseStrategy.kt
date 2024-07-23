package org.saturn.generator.messageconsructor.parser.strategy.separator

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.Mirror
import org.saturn.generator.messageconsructor.data.variable.Address
import org.saturn.generator.messageconsructor.data.variable.Command
import org.saturn.generator.messageconsructor.data.variable.Data
import org.saturn.generator.messageconsructor.parser.strategy.CheckResult
import org.saturn.generator.messageconsructor.parser.strategy.ParseStrategy
import org.saturn.generator.usefuldata.UsefulData

class LengthParseStrategy(private val parts: List<Part>) : ParseStrategy {

    private val mirror : Int = if(parts.any { it is Mirror }) parts.first { it is Mirror }.getBytesInMessage()[0] else -1
    override fun check(data: List<Int>): CheckResult {
        TODO("Not yet implemented")
    }

    override fun parse(messageData: List<Int>): UsefulData {
        val items = getItems(messageData)
        var data = listOf<Int>()
        var command = listOf<Int>()
        var address = listOf<Int>()
        var dataIndex = parts.indexOfFirst { it is Data }
        var commandIndex = parts.indexOfFirst { it is Command }
        var addressIndex = parts.indexOfFirst { it is Address }
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
        fun getLengthMinVaLue() : Int
        {
            val lengthPart = parts.first { it is Length } as Length
            return lengthPart.dependsOn.filter { it.bytesInPartCount>=0 }.sumOf { it.bytesInPartCount }
        }

        val items : MutableList<MutableList<Int>> = mutableListOf()
        var partNumber = 0
        var length = 0
        (0..data.lastIndex).forEach { index->
            if(parts[partNumber] is Length)
            {
                //:TODO field can be more than 1 byte long
                length=data[index] - getLengthMinVaLue()
            }
            if(parts[partNumber].bytesInPartCount<0 && items.last().size ==length)
            {
                items.add(mutableListOf())
                partNumber++
            }
            else if(items.last().size == parts[partNumber].getLength())
            {
                items.add(mutableListOf())
                partNumber++
            }

            items.last().add(data[index])
        }
        return items
    }
}