package org.saturn.generator.messageconsructor.data.depends

import org.saturn.generator.messageconsructor.data.Part

abstract class DependsPart(public val dependsOn : List<Part>, bytesInPartCount : Int) : Part(bytesInPartCount = bytesInPartCount)
{
    abstract fun count()

}