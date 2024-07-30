package org.saturn.generator.messageconsructor

import org.saturn.generator.messageconsructor.data.Part
import org.saturn.generator.messageconsructor.data.depends.length.Length
import org.saturn.generator.messageconsructor.data.hardcode.onebyte.*
import org.saturn.generator.messageconsructor.data.variable.Address
import org.saturn.generator.messageconsructor.data.variable.Command
import org.saturn.generator.messageconsructor.data.variable.Data
import org.saturn.generator.messageconsructor.data.variable.VariablePart
import org.yaml.snakeyaml.Yaml

object MessageStructFactory {

    private val yaml = Yaml()

    fun fromYaml(yamlLine : String) : MessageStruct
    {
        val data: Map<String, Any> = yaml.load(yamlLine)
        val version : String = data["version"] as String
        val struct : List<String> = data["struct"] as List<String>
        val parts : Map<String, Map<String, Any>> = data["parts"] as Map<String, Map<String, Any>>
        //add not depends on
        val partToPartName : MutableMap<String, Part> = parts.filter {(structName, partMap )->
            partMap==null || !partMap.containsKey("dependsOn")
        }.map{(structName, partMap )->
            val part : Part = when(structName)
            {
                "mirror"->{
                    getMirror(partMap)
                }
                "prefix"->{
                    getPrefix(partMap)
                }
                "separator"->{
                    getSeparator(partMap)
                }
                "suffix"->{
                    getSuffix(partMap)
                }
                "address"->{
                    getAddress(partMap)
                }
                "command"->{
                    getCommand(partMap)
                }
                "data"->{
                    getData(partMap)
                }
                else->{throw Exception("Part ${structName} not support")}
            }
            Pair(structName, part)
        }.toMap().toMutableMap()
        //add depends on
        while(!parts.keys.all { partToPartName.containsKey(it) }) {
            parts.filter { (structName, partMap) ->
                partMap!=null && partMap.containsKey("dependsOn")
            }.filter { (structName, partMap) ->
                val dependList = partMap["dependsOn"] as List<String>
                dependList.all { partToPartName.containsKey(it) }
            }.forEach {(structName, partMap) ->
                val part : Part = when(structName)
                {
                    "length"->{getLength(partMap, partToPartName)}
                    else->{throw Exception("Part ${structName} not support")}
                }
                partToPartName[structName]=part
            }
        }

        val sortedParts : List<Part> = struct.map { partName->  partToPartName[partName]!!}.toList()
        return MessageStruct(sortedParts)
    }

    private fun getMirror(options : Map<String, Any>) : Mirror
    {
        val value  :Int = options["value"] as Int
        return Mirror(value)
    }

    private fun getPrefix(options : Map<String, Any>) : Prefix
    {
        val value  :Int = options["value"] as Int
        return Prefix(value)
    }

    private fun getSeparator(options : Map<String, Any>) : Separator
    {
        val value  :Int = options["value"] as Int
        return Separator(value)
    }

    private fun getSuffix(options : Map<String, Any>) : Suffix
    {
        val value  :Int = options["value"] as Int
        return Suffix(value)
    }

    private fun getAddress(options : Map<String, Any>) : Address
    {
        val bytesCountKey = "bytesCount"
        val value  :Int =
            if(options!=null && options.containsKey(bytesCountKey))
                options[bytesCountKey] as Int
            else
                -1
        return Address(value)
    }

    private fun getData(options : Map<String, Any>) : Data
    {
        val bytesCountKey = "bytesCount"
        val value  :Int =
            if(options!=null && options.containsKey(bytesCountKey))
                options[bytesCountKey] as Int
            else
                -1
        return Data(value)
    }

    private fun getCommand(options : Map<String, Any>) : Command
    {
        val bytesCountKey = "bytesCount"
        val value  :Int =
            if(options!=null && options.containsKey(bytesCountKey))
                options[bytesCountKey] as Int
            else
                -1
        return Command(value)
    }

   /*
    private fun <T : OneByteHardcodePart>getOneByteHardcodePart(options : Map<String, Any>) : T
    {
        val value  :Int = options["value"] as Int
        return OneByteHardcodePart(value) as T
    }

    private fun <T: VariablePart>getVariablePart(options : Map<String, Any>) : T
    {
        val bytesCountKey = "bytesCount"
        val value  :Int =
            if(options!=null && options.containsKey(bytesCountKey))
                options[bytesCountKey] as Int
            else
                -1
        return VariablePart(value) as T
    }

    */


    private fun getLength(options : Map<String, Any>, partToPartName : MutableMap<String, Part>) :Length
    {
        val key = "bytesCount"
        val bytesCount  :Int =
            if(options.containsKey(key))
                options[key] as Int
            else
                -1
        val dependsOnList = options["dependsOn"] as List<String>
        val dependsOnPatsList = dependsOnList.map {dependsOnName-> partToPartName[dependsOnName]!! }.toList()
        return Length(dependsOnPatsList, bytesCount)
    }

}