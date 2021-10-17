import java.io.File

typealias Database = MutableMap<String, String>

data class DatabaseClass(var pathToDatabase : String) {

    private var password: String? = null
    private val db: Database

    init {
        if (File(pathToDatabase).exists()) {
            val checkPasswordResult = checkPassword(pathToDatabase) { tryToRead() }
            if (!checkPasswordResult.guessed) {
                throw WrongPasswords()
            }
            password = checkPasswordResult.password
            if (password != null) {
                println("Loading database")
            }
        }

        try {
            db = loadDatabase()
        } catch (e: Exception) {
            throw e
        }
        rebuildDatabaseFile()
    }

    private fun addToFileNewLine(line: String) {
        val strs = line.split(' ')
        for (i in strs.indices) {
            File(pathToDatabase).appendText(hashByKey(strs[i], password))
            if (i != strs.lastIndex) {
                File(pathToDatabase).appendText(" ")
            }
        }
        File(pathToDatabase).appendText("\n")
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

    fun setNewPassword(str: String?) {
        password = str
        try {
            rebuildDatabaseFile()
        } catch (e: Exception) {
            throw e
        }
    }

    fun add(key : String, value : String) {
        db[key] = value
        addToFileNewLine("a $key $value")
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
            addToFileNewLine("d $key")
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
            val str = it.split(' ').map { unleashByKey(it, password)}
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
                addToFileNewLine("a ${it.key} ${it.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}