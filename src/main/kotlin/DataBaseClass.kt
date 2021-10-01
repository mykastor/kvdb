import java.io.File
import kotlin.jvm.internal.Intrinsics

typealias DataBase = MutableMap<String, String>

data class DataBaseClass(var pathToDatabase : String) {

    private val db = loadDataBase()

    init {
        rebuildDataBase()
    }

    private fun addToFile(str: String) {
        File(pathToDatabase).appendText(str)
    }

    private fun initDataBase() {
        if (!File(pathToDatabase).exists()) {
            File(pathToDatabase).appendText("init\n")
        }
    }

    fun cleanDataBaseFile() {
        assert(File(pathToDatabase).exists()) {
            "removeDataBase used when there is no database"
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
        rebuildDataBase()
        File(oldPath).delete()
    }

    fun remove(key : String) {
        if (db.containsKey(key)) {
            db.remove(key)
            addToFile("d $key\n")
        }
    }

    fun find(key : String) : String? {
        return if (db.containsKey(key)) {
            db[key]
        } else {
            null
        }
    }

    private fun loadDataBase(): DataBase {
        initDataBase()
        val lines = File(pathToDatabase).readLines()
        val db = mutableMapOf<String, String>()
        lines.forEach {
            val str = it.split(' ')
            when (str[0]) {
                "a" -> {
                    if (str.size != 3) {
                        logger.error { "Database is damaged. $str" }
                    }
                    db[str[1]] = str[2]
                }
                "d" -> {
                    if (str.size != 2) {
                        logger.error { "Database is damaged. $str" }
                    }
                    db.remove(str[1])
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

    fun rebuildDataBase() {
        initDataBase()
        cleanDataBaseFile()
        db.forEach {
            addToFile("a ${it.key} ${it.value}\n")
        }
    }
}