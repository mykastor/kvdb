import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

var logger = KotlinLogging.logger {}
const val pathToPath = "paths/path.txt"
const val defaultPathToDatabase = "database/db.txt"

fun main(args: Array<String>) {
    val pathToDatabase = getPath()
    logger.info {"Path to database: $pathToDatabase"}
    val db = DatabaseClass(pathToDatabase)
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
