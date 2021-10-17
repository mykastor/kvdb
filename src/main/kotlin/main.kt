import mu.KotlinLogging
import kotlin.system.exitProcess

var logger = KotlinLogging.logger {}

fun tryToRead() : String {
    return readLine() ?: throw NoInput()
}

var db: DatabaseClass? = null

fun chooseDataBase() {
    println("Please, choose or create a database")
    printAllDatabaseNames()
    while (db == null) {
        val cmd: String
        try {
            cmd = tryToRead()
        } catch (e: Exception) {
            logger.error(e) { e }
            println(e)
            exitProcess(0)
        }
        val strs = cmd.split(' ')
        if (strs.size != 2) {
            println("Wrong input format")
            continue
        }
        try {
            when (strs[0]) {
                "create" -> db = createNewDatabase(strs[1])
                "choose" -> db = getPath(strs[1])?.let { DatabaseClass(it) }
                else -> println("Wrong input format")
            }
        } catch (e: Exception) {
            logger.error(e) { e }
            println(e)
            exitProcess(0)
        }
    }
}

fun main(args: Array<String>) {

    while (true) {
        if (db == null) {
            chooseDataBase()
        } else {
            val cmd: String
            try {
                cmd = tryToRead()
            } catch (e: Exception) {
                logger.error(e) { e }
                println(e)
                return
            }
            val str = cmd.split(' ')
            try {
                when (str[0]) {
                    "out" -> db = null
                    "add" -> add(db!!, str)
                    "remove" -> remove(db!!, str)
                    "find" -> find(db!!, str)
                    "exit" -> exitProcess(0)
                    "clean!!" -> db!!.cleanDataBase()
                    "help" -> help()
                    "path?" -> println(db!!.pathToDatabase)
                    "set" -> setNewPassword(db!!, str)
                    "all" -> db!!.printAll()
                    "rmpwd" -> db!!.setNewPassword(null)
                    else -> println("Wrong command. Use \"help\" to see available commands")
                }
            } catch (e: Exception) {
                logger.error(e) { e }
                println(e)
            }
        }
    }
}
