package org.saturn.generator.messageconsructor.data

/**
 * Message part/field.
 * etc. prefix (start byte), length or command
 * @param bytesInPartCount Number of bytes reserved for this field, -1 undefined
 */
abstract class Part(protected var bytes : List<Int> = listOf(), val bytesInPartCount: Int = -1) {
    fun getLength() : Int
    {
        if(bytesInPartCount<=0)
            return bytes.size
        else
            return bytesInPartCount
    }

    fun getBytesInMessage()  : List<Int>
    {
        if(bytesInPartCount<=0)
            return bytes
        else if(bytesInPartCount<bytes.size)
        {
            throw  Exception("bytesInPartCount<bytes.size")
        }
        else
        {
            val zeroCount = bytesInPartCount-bytes.size
            val zeros = mutableListOf<Int>()
            (0..<zeroCount).forEach { zeros.add(0) }
            zeros.addAll(bytes)
            return zeros
        }
    }
}