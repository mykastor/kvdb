import java.io.File

typealias Database = MutableMap<String, String>

data class DatabaseClass(var pathToDatabase : String) {

    private val db = loadDatabase()

    init {
        rebuildDatabaseFile()
    }

    private fun addToFile(str: String) {
        File(pathToDatabase).appendText(str)
    }

    private fun initDataBase() {
        if (!File(pathToDatabase).exists()) {
            logger.info {"Creating $pathToDatabase file"}
            File(pathToDatabase).appendText("init\n")
        }
    }

    private fun cleanDataBaseFile() {
        if (!File(pathToDatabase).exists()) {
            throw DatabaseFilesDoesNotExist(pathToDatabase)
        }
        File(pathToDatabase).writeText("init\n")
    }

    fun cleanDataBase() {
        cleanDataBaseFile()
        db.clear()
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
        rebuildDatabaseFile()
        File(oldPath).delete()
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
        lines.forEach {
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
                "init" -> {

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