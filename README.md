Worked a lot with various devices via serial interfaces (RS485, RS232). Quite often I was faced with the need to support one or another modbas-like protocol and each time I had to almost write a parser and message generator from scratch. The idea came to make a certain language for describing these protocols and configure an interpreter and generator based on a file written in this language. I implement most of the projects in Kotlin (yes, I know that only Back and Android are currently written in it, but despite this, many embedded devices with a more or less full-fledged OS use the JVM), so this language was chosen to write a <b>pre-prototype</b> of the interpreter. In order not to invent my own syntax, I chose yaml as a markup language. In a couple of evenings I wrote the first pre-prototype of the library to evaluate the complexity and pitfalls.
``` yaml
version: 0.0.1
struct: [prefix, separator, address, separator, command, separator, data, separator, suffix]
parts:
  prefix:
    value: 1
  suffix:
    value: 2
  address:
    bytesCount : 4
  command :
    bytesCount : 2
  separator :
    value : 255
  data:
``` 
<i>(Example protocol description 1)</i>

``` yaml
version: 0.0.1
struct: [prefix, address, length, command, data, suffix]
parts:
  prefix:
    value: 1
  suffix:
    value: 2
  address:
    bytesCount : 4
  command :
    bytesCount : 2
  length :
    dependsOn : [command, data]
  data:
``` 
<i>(Example protocol description 2)</i>
``` kotlin
 @Test
    fun exampleTest()
    {
        //read the protocol structure
        val filename = "TestData/ExampleLength.yaml"
        val text = File(filename).readText()
        val messageStruct = MessageStructFactory.fromYaml(text)

        //generate a message (sequence of bytes)
        val message : List<Int> = messageStruct.setAddress(listOf(13, 18)).setCommand(listOf(1)).setData(listOf(12,13,14,15,16,17)).build()
        println("Generated message : $message")// [1, 0, 0, 13, 18, 8, 0, 1, 12, 13, 14, 15, 16, 17, 2]

        //parse the message
        val parser = Parser(messageStruct.parts)
        val checkResult = parser.check(message)
        checkResult.errorList.forEach { println(it) }
        //check the message in case, for example, it was broken during transmission
        if(checkResult.success) {
            //if the message passed the check, extract the useful data
            val usefulData : UsefulData = parser.parse(message)
            println(usefulData)//UsefulData(address=[0, 0, 13, 18], command=[0, 1], data=[12, 13, 14, 15, 16, 17])
        }
        else
        {
            //do something else if it doesn’t work
            //return error
        }
    }
``` 
<i>(Example using interpreter 1)</i><br>
I am writing to understand how interesting this topic is and whether it is worth developing the project in the future.
