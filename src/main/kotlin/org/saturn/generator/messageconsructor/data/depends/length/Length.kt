package org.saturn.generator.messageconsructor.data.depends.length

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.DependsPart

class Length(dependsOn : List<Part>, bytesInPartCount : Int) : DependsPart(dependsOn, bytesInPartCount) {
    override fun count() {
        val value : Int = dependsOn.sumOf { it.getLength() }
        //:TODO Сделать разложение на байты
        bytes = listOf(value)
    }
}