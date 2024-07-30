package org.saturn.generator.messageconsructor.parser.strategy

/**
 * The result of checking the message for validity.
 */
data class CheckResult(val success : Boolean, val errorList: List<String> = listOf())
