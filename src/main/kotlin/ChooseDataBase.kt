import kotlin.system.exitProcess

fun chooseDataBase() {
    println("Please, choose or create a database.")
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
        if (strs[0] == "help") {
            help()
            continue
        }
        if (strs.size != 2) {
            println("Wrong input format.")
            continue
        }
        try {
            when (strs[0]) {
                "create" -> db = createNewDatabase(strs[1])
                "choose" -> db = getDatabase(strs[1])
                else -> println("Wrong input format.")
            }
        } catch (e: Exception) {
            logger.error(e) { e }
            println(e)
            exitProcess(0)
        }
    }
}