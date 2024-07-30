package org.saturn.generator.messageconsructor.data.hardcode

import org.saturn.generator.messageconsructor.data.Part

/**
 * A part that always has the same meaning.
 * etc. prefix (start byte), suffix(stop byte)
 */
abstract class HardcodePart(bytes : List<Int>, length: Int = -1) : Part(bytes, length)