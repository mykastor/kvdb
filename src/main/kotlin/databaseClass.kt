import java.io.File
import javax.xml.crypto.Data

typealias Database = MutableMap<String, String>

fun getDatabase(pathToDatabase: String) : DatabaseClass {
    try {
        if (File(pathToDatabase).exists() && !checkPassword(pathToDatabase) { tryToRead() }) {
            throw WrongPasswords()
        }
    } catch (e: Exception) {
        throw e
    }
    return DatabaseClass(pathToDatabase)
}

data class DatabaseClass(var pathToDatabase : String) {

    private var password: String? = null
    private val db: Database

    init {
        try {
            db = loadDatabase()
        } catch (e: Exception) {
            throw e
        }
        rebuildDatabaseFile()
    }

    private fun addToFile(str: String) {
        File(pathToDatabase).appendText(str)
    }

    private fun initDataBase() {
        if (!File(pathToDatabase).exists()) {
            logger.info {"Creating $pathToDatabase file"}
            File(pathToDatabase).appendText(hashPassword(password).toString() + "\n")
        }
    }

    private fun cleanDataBaseFile() {
        if (!File(pathToDatabase).exists()) {
            throw DatabaseFilesDoesNotExist(pathToDatabase)
        }
        File(pathToDatabase).writeText(hashPassword(password).toString() + "\n")
    }

    fun cleanDataBase() {
        try {
            cleanDataBaseFile()
        } catch (e: Exception) {
            throw e
        }
        db.clear()
    }

    // выводит первые 100 элементов
    fun printAll() {
        var cnt = 100
        db.forEach {
            if (cnt == 0) return
            cnt -= 1
            println("${it.key}: ${it.value}")
        }
    }

    fun setPassword(str: String) {
        password = str
        try {
            rebuildDatabaseFile()
        } catch (e: Exception) {
            throw e
        }
    }

    fun add(key : String, value : String) {
        db[key] = value
        addToFile("a $key $value\n")
    }
    
    fun changePath(newPathToDatabase : String) {
        if (newPathToDatabase == pathToDatabase) {
            return
        }

        val oldPath = pathToDatabase
        pathToDatabase = newPathToDatabase

        try {
            rebuildDatabaseFile()
        } catch (e: Exception) {
            throw e
        }

        if (File(oldPath).exists()) {
            File(oldPath).delete()
        }
    }

    fun remove(key : String) {
        if (db.containsKey(key)) {
            db.remove(key)
            addToFile("d $key\n")
        }
    }

    fun find(key : String) : String? {
        return db[key]
    }

    private fun loadDatabase(): Database {
        initDataBase()
        val lines = File(pathToDatabase).readLines()
        val db = mutableMapOf<String, String>()
        lines.subList(1, lines.size).forEach {
            val str = it.split(' ')
            when (str[0]) {
                "a" -> {
                    if (str.size != 3) {
                        logger.error { "Database is damaged. $str" }
                    } else {
                        db[str[1]] = str[2]
                    }
                }
                "d" -> {
                    if (str.size != 2) {
                        logger.error { "Database is damaged. $str" }
                    } else {
                        db.remove(str[1])
                    }
                }
                else -> {
                    logger.error { "Database is damaged. $str" }
                }
            }
        }
        return db
    }

    fun rebuildDatabaseFile() {
        try {
            initDataBase()
            cleanDataBaseFile()
            db.forEach {
                addToFile("a ${it.key} ${it.value}\n")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}