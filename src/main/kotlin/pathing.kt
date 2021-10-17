import java.io.File


const val pathToPath = "paths.txt"
const val defaultPathToDatabase = "database/db.txt"

const val defaultPath = "db.txt"

fun getPath() : String {
    if (File(pathToPath).exists()) {
        File(pathToPath).readLines().forEach {
            return it
        }
        logger.error { "File which contains a path to database is empty."}
    } else {
        logger.error { "Didn't find a file or which contains a path to databases. Current path to that file: $pathToPath\". " +
                "Default path to database is used." }
        File(pathToPath).writeText(defaultPath)
    }
    return defaultPath
}
