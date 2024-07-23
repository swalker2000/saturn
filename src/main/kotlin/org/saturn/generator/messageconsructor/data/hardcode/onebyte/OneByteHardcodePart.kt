package org.saturn.generator.messageconsructor.data.constant.onebyte

import org.saturn.generator.messageconsructor.data.constant.HardcodePart

abstract class OneByteHardcodePart(byte : Int) : HardcodePart(listOf(byte))