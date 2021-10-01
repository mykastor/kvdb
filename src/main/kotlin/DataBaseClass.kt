import java.io.File
import kotlin.jvm.internal.Intrinsics

const val PATH_TO_DATABASE = "database/db.txt"

typealias DataBase = MutableMap<String, String>

data class DataBaseClass(val PATH_TO_DB : String) {

    private val db = loadDataBase()

    init {
        rebuildDataBase()
    }

    private fun addToFile(str: String) {
        File(PATH_TO_DB).appendText(str)
    }

    private fun initDataBase() {
        if (!File(PATH_TO_DB).exists()) {
            File(PATH_TO_DB).appendText("init\n")
        }
    }

    fun cleanDataBase() {
        assert(File(PATH_TO_DB).exists()) {
            "removeDataBase used when there is no database"
        }
        File(PATH_TO_DB).writeText("init\n")
    }

    fun add(key : String, value : String) {
        db[key] = value
        addToFile("a $key $value\n")
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
        val lines = File(PATH_TO_DB).readLines()
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
        cleanDataBase()
        db.forEach {
            addToFile("a ${it.key} ${it.value}\n")
        }
    }
}