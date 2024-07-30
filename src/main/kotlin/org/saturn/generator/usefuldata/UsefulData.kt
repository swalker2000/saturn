package org.saturn.generator.usefuldata

/**
 * Useful data transmitted in the message.
 */
data class UsefulData(val address : List<Int>, val command : List<Int>, val data : List<Int>)
