package org.saturn.generator.messageconsructor.parser.strategy


data class CheckResult(val success : Boolean, val errorList: List<String> = listOf())
