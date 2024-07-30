import org.junit.jupiter.api.Test
import org.saturn.generator.messageconsructor.MessageStructFactory
import org.saturn.generator.messageconsructor.parser.Parser
import org.saturn.generator.messageconsructor.parser.strategy.CheckResult
import org.saturn.generator.usefuldata.UsefulData
import java.io.File

class MessageStructFactoryTest {

    @Test
    fun smokeTestLength()
    {
        val filename = "TestData/ExampleLength.yaml"
        generateThanCheck(filename)
    }


    @Test
    fun smokeTestSeparator()
    {
        val filename = "TestData/ExampleSeparator.yaml"
        generateThanCheck(filename)
    }

    @Test
    fun exampleTest()
    {
        //считываем структуру протокола
        val filename = "TestData/ExampleLength.yaml"
        val text = File(filename).readText()
        val messageStruct = MessageStructFactory.fromYaml(text)

        //генерируем сообщение (последовательность байт)
        val message : List<Int> = messageStruct.setAddress(listOf(13, 18)).setCommand(listOf(1)).setData(listOf(12,13,14,15,16,17)).build()
        println("Generated message : $message")// [1, 0, 0, 13, 18, 8, 0, 1, 12, 13, 14, 15, 16, 17, 2]

        //парсим сообщение
        val parser = Parser(messageStruct.parts)
        val checkResult = parser.check(message)
        checkResult.errorList.forEach { println(it) }
        //проверяем сообщение на случай если к примеру оно побилось при передаче
        if(checkResult.success) {
            //если сообщение прошло проверку извлекаем полезные данные
            val usefulData : UsefulData = parser.parse(message)
            println(usefulData)//UsefulData(address=[0, 0, 13, 18], command=[0, 1], data=[12, 13, 14, 15, 16, 17])
        }
        else
        {
            //предпринимаем что-то еще если не прошло
            //return error
        }
    }

    private fun generateThanCheck(filename : String)
    {
        val text = File(filename).readText()
        val messageStruct = MessageStructFactory.fromYaml(text)
        val message = messageStruct.setAddress(listOf(13, 18)).setCommand(listOf(1)).setData(listOf(12,13,14,15,16,17)).build()
        println(message)
        val parser = Parser(messageStruct.parts)
        val checkResult = parser.check(message)
        checkResult.errorList.forEach { println(it) }
        assert(parser.check(message).success)
        val usefulData = parser.parse(message)
        println(usefulData)
    }
}