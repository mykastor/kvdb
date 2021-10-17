import java.io.File


const val pathToPath = "paths.txt"
const val defaultPathToDatabase = "database/db.txt"

fun changePath(db : DatabaseClass, str : List<String>) {
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

const val defaultPath = "db.txt"

fun getPath() : String {
    if (File(pathToPath).exists()) {
        File(pathToPath).readLines().forEach {
            return it
        }
        logger.error { "File which contains a path to database is empty."}
    } else {
        logger.error { "Didn't find a file or which contains a path to database. Current path to that file: $pathToPath\". " +
                "Default path to database is used." }
        File(pathToPath).writeText(defaultPath)
    }
    return defaultPath
}
