package org.saturn.generator.messageconsructor.data.constant

import org.saturn.generator.messageconsructor.data.Part

abstract class HardcodePart(bytes : List<Int>) : Part() {

    init {
        super.bytes = bytes
    }
}