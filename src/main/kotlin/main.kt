import java.io.File
import kotlin.system.exitProcess

const val BOUND_TO_REBUILD = 1e5

typealias DataBase = MutableMap<String, String>

data class DataBaseClass(val PATH_TO_DB : String) {

    private val db = loadDataBase()

    fun addToFile(str: String) {
        File(PATH_TO_DB).appendText(str)
    }

    private fun initDataBase() {
        if (!File(PATH_TO_DB).exists()) {
            File(PATH_TO_DB).appendText("init database123\n")
        }
    }

    fun cleanDataBase() {
        assert(File(PATH_TO_DB).exists()) {
            "removeDataBase used when there is no database"
        }
        File(PATH_TO_DB).writeText("init database\n")
    }

    private fun askToRebuild() {
        println("do you want to rebuild database? (it might take some time) [y; n]")
        println("it happened because the program did not close with the \"exit\" command")
        val str = readLine() ?: return
        if (str.first().lowercase() == "y") {
            rebuildDataBase()
        }
    }

    fun add(key : String, value : String) {
        db[key] = value
        addToFile("add $key $value\n")
    }

    fun remove(key : String) {
        if (db.containsKey(key)) {
            db.remove(key)
            addToFile("delete $key\n")
        }
    }

    fun find(key : String) {
        if (db.containsKey(key)) {
            println(db[key])
        } else {
            println("There is no such key")
        }
    }

    private fun loadDataBase(): DataBase {
        initDataBase()
        val lines = File(PATH_TO_DB).readLines()
        val db = mutableMapOf<String, String>()
        var deleteCount = 0
        lines.forEach {
            val str = it.split(' ')
            when (str[0]) {
                "add" -> {
                    db[str[1]] = str[2]
                }
                "delete" -> {
                    deleteCount += 1
                    db.remove(str[1])
                }
            }
        }
        if (deleteCount >= BOUND_TO_REBUILD) {
            askToRebuild()
        }
        return db
    }

    fun rebuildDataBase() {
        // TODO
    }
}

fun main(args: Array<String>) {
    val db = DataBaseClass("database/db.txt")

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
                db.find(str[1])
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
