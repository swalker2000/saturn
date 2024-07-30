package org.saturn.generator.messageconsructor.data.variable

import org.saturn.generator.messageconsructor.data.Part

open class VariablePart(bytesInPartCount : Int = -1) : Part(bytesInPartCount = bytesInPartCount) {
    fun setBytesInPart(bytes : List<Int>)
    {
        super.bytes=bytes
    }

    fun clear()
    {
        bytes = listOf()
    }
}