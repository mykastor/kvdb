import mu.KotlinLogging
import java.io.File
import kotlin.math.log
import kotlin.system.exitProcess

var logger = KotlinLogging.logger {}

fun tryToRead() : String {
    return readLine() ?: throw NoInput()
}

fun main(args: Array<String>) {

    val pathToDatabase = getPath()
    logger.info {"Path to database: $pathToDatabase"}

    val db: DatabaseClass?
    try {
        db = DatabaseClass(pathToDatabase)
    } catch (e: WrongPasswords) {
        logger.error(e) {e}
        println(e)
        return
    }

    while (true) {
        val cmd: String
        try {
            cmd = tryToRead()
        } catch (e: Exception) {
            logger.error(e) {e}
            println(e)
            return
        }
        val str = cmd.split(' ')
        try {
            when (str[0]) {
                "add" -> add(db, str)
                "remove" -> remove(db, str)
                "find" -> find(db, str)
                "exit" -> exitProcess(0)
                "clean!!" -> db.cleanDataBase()
                "help" -> help()
                "changepath" -> changePath(db, str)
                "path?" -> println(db.pathToDatabase)
                "set" -> setNewPassword(db, str)
                "all" -> db.printAll()
                "rmpwd" -> db.setNewPassword(null)
                else -> println("Wrong command. Use \"help\" to see available commands")
            }
        } catch (e: Exception) {
            logger.error(e) {e}
            println(e)
        }
    }
}
