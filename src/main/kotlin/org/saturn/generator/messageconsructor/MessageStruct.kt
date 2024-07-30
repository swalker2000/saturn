package org.saturn.generator.messageconsructor

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.DependsPart
import org.saturn.generator.messageconsructor.data.variable.Address
import org.saturn.generator.messageconsructor.data.variable.Command
import org.saturn.generator.messageconsructor.data.variable.Data
import org.saturn.generator.messageconsructor.data.variable.VariablePart

class MessageStruct(val parts: List<Part>)
{
    val version : String = "0.0.1"

    fun clear()
    {
        parts.filterIsInstance<VariablePart>().forEach { it.clear() }
    }

    fun build() : List<Int>
    {
        parts.filterIsInstance<DependsPart>().forEach { it.count() }
        return parts.flatMap { it.getBytesInMessage() }.toList()
    }

    fun setAddress(address: List<Int>) : MessageStruct
    {
        return setValue<Address>(address)
    }
    fun setCommand(command: List<Int>)  :MessageStruct
    {
        return setValue<Command>(command)
    }
    fun setData(command: List<Int>) :MessageStruct
    {
        return setValue<Data>(command)
    }

    private inline fun <reified T: VariablePart> setValue(value: List<Int>) : MessageStruct
    {
        if(parts.none { it is T })
        {
            throw Exception("Part ${T::class} not found")
        }
        parts.filterIsInstance<T>().forEach { it.setBytesInPart(value)}
        return this
    }



}