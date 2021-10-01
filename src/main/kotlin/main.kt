import mu.KotlinLogging
import kotlin.system.exitProcess

var logger = KotlinLogging.logger {}

fun add(db : DataBaseClass, str : List<String>) {
    if (str.size != 3) {
        println("Please, enter input format in this format: \"add key value\".\nNotice that key and value cannot have whitespaces")
        return
    }
    db.add(str[1], str[2])
}

fun remove(db : DataBaseClass, str : List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"remove key\".\nNotice that key cannot have whitespaces")
        return
    }
    db.remove(str[1])
}

fun find(db : DataBaseClass, str : List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"find key\".\nNotice that key cannot have whitespaces")
        return
    }
    val res = db.find(str[1])
    if (res == null) println("There is no such element")
    else println(res)
}

fun help() {
    println("Hello! It's a key-value database. Command you can use:")
    println("add key value - adds an element to the database, if it already exists, replaces it with a new value")
    println("find key - find an element by key")
    println("remove key - remove an element by key, if there is no such element nothing will happen")
    println("clean!! - clean the database")
    println("Notice that _key_ and _value_ are strings without whitespaces")
}

fun main(args: Array<String>) {
    val db = DataBaseClass(PATH_TO_DATABASE)
    while (true) {
        val cmd = readLine() ?: return
        val str = cmd.split(' ')
        when (str[0]) {
            "add" -> add(db, str)
            "remove" -> remove(db, str)
            "find" -> find(db, str)
            "exit" -> exitProcess(0)
            "clean!!" -> db.cleanDataBase()
            "help" -> help()

            else -> println("Wrong command. Use \"help\" to see available commands")
        }
    }
}
