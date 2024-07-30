package org.saturn.generator.messageconsructor.data.hardcode

import org.saturn.generator.messageconsructor.data.Part

abstract class HardcodePart(bytes : List<Int>, length: Int = -1) : Part(bytes, length)