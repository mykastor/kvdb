import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

var logger = KotlinLogging.logger {}
const val pathToPath = "paths/path.txt"
const val defaultPathToDatabase = "database/db.txt"

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
    println("[add key value] - adds an element to the database, if it already exists, replaces old value with new")
    println("[find key] - find an element by key")
    println("[remove key] - remove an element by key, if there is no such element nothing will happen")
    println("[clean!!] - clean the database")
    println("[changepath path] - change the path to database")
    println("[path?] - print the current path to database")
    println("Notice that [key] and [value] are strings without whitespaces")
}

fun changePath(db : DataBaseClass, str : List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"rename path\".\nNotice that path cannot have whitespaces")
        return
    }
    if (str[1] == "-d") {
        File(pathToPath).writeText(defaultPathToDatabase)
        db.changePath(defaultPathToDatabase)
    } else {
        File(pathToPath).writeText(str[1])
        db.changePath(str[1])
    }
}

fun getPath() : String {
    if (File(pathToPath).exists()) {
        File(pathToPath).readLines().forEach {
            return it
        }
        logger.error { "File which contains a path to database is empty."}
    } else {
        logger.error { "Didn't find a file or which contains a path to database. Current path to that file: $pathToPath\". " +
                "Default path to database is used." }
    }
    return "database/db.txt"
}

fun main(args: Array<String>) {
    val pathToDatabase = getPath()
    logger.info {"Path to database: $pathToDatabase"}
    val db = DataBaseClass(pathToDatabase)
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
            "changepath" -> changePath(db, str)
            "path?" -> println(db.pathToDatabase)
            else -> println("Wrong command. Use \"help\" to see available commands")
        }
    }
}
