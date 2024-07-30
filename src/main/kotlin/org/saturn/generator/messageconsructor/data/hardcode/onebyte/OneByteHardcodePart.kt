package org.saturn.generator.messageconsructor.data.hardcode.onebyte

import org.saturn.generator.messageconsructor.data.hardcode.HardcodePart


/**
 * A part that always has the same meaning and length 1 byte.
 * etc. prefix (start byte), suffix(stop byte)
 */open class OneByteHardcodePart(byte : Int) : HardcodePart(listOf(byte), 1)