import java.io.File

private const val pathToPath = "paths.txt"
private const val defaultPath = "db.txt"

fun addNewPath(databaseName: String, pathToDatabase: String) {
    if (!File(pathToPath).exists()) {
        File(pathToPath).createNewFile()
    }
    File(pathToPath).appendText("$databaseName $pathToDatabase\n")
}

fun getNewPath(databaseName: String) : String {
    if (!File("database").exists()) {
        File("database").mkdir()
    }
    if (File("database").isDirectory) {
        for(i in 1..100) {
            val path = "database/db_name_$databaseName$i.txt"
            if (!File(path).exists()) {
                addNewPath(databaseName, path)
                return path
            }
        }
        throw NameIsTaken("database/db_name_$databaseName")
    } else {
        throw NameIsTaken("database")
    }
}

fun getPath(databaseName: String) : String? {
    if (!File(pathToPath).exists()) {
        File(pathToPath).createNewFile()
    }

    File(pathToPath).readLines().forEach {
        val strs = it.split(' ')
        if (strs.size == 2) {
            if (strs[0] == databaseName) {
                return strs[1]
            }
        } else {
            logger.error { "File which contains paths is damaged : $it."}
        }
    }

    logger.debug { "File which contains paths to databases doesn't have a path to $databaseName." }

    return null
}


fun getAllDatabaseNames() : List<String> {
    return if (File(pathToPath).exists()) {
        val names = mutableListOf<String>()
        File(pathToPath).readLines().forEach {
            if (it.isNotEmpty()) {
                names.add(it.split(' ').first())
            }
        }
        names
    } else {
        listOf()
    }
}