import java.io.File
import kotlin.random.Random
import kotlin.system.exitProcess
fun main(args: Array<String>) {
    val db = DataBaseClass(PATH_TO_DATABASE)

    while (true) {
        val cmd = readLine() ?: return
        val str = cmd.split(' ')
        when (str[0]) {
            "add" -> {
                if (str.size != 3) {
                    println("Please, enter input format in this format: \"add key value\".\nNotice that key and value cannot have whitespaces")
                    continue
                }
                db.add(str[1], str[2])
            }
            "remove" -> {
                if (str.size != 2) {
                    println("Please, enter input format in this format: \"remove key\".\nNotice that key cannot have whitespaces")
                    continue
                }
                db.remove(str[1])
            }
            "find" -> {
                if (str.size != 2) {
                    println("Please, enter input format in this format: \"find key\".\nNotice that key cannot have whitespaces")
                    continue
                }
                val res = db.find(str[1])
                if (res == null) println("There is no such element")
                else println(res)
            }
            "exit" -> {
                println("Please, wait. Database is rebuilding now. It might take some time")
                db.rebuildDataBase()
                exitProcess(0)
            }
            "clean!!" -> {
                db.cleanDataBase()
            }
            "help" -> {
                println("Hello! It's a key-value database. Command you can use:")
                println("add key value - adds an element to the database, if it already exists, replaces it with a new value")
                println("find key - find an element by key")
                println("remove key - remove an element by key, if there is no such element nothing will happen")
                println("clean!! - clean the database")
                println("Notice that _key_ and _value_ are strings without whitespaces")
            }
            else -> {
                println("Wrong command. Use \"help\" to see available commands")
            }
        }
    }
}
