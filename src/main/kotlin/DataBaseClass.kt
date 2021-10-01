import java.io.File

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
            File(PATH_TO_DB).appendText("init database\n")
        }
    }

    fun cleanDataBase() {
        assert(File(PATH_TO_DB).exists()) {
            "removeDataBase used when there is no database"
        }
        File(PATH_TO_DB).writeText("init database\n")
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
                    assert(str.size == 3) {
                        "Database is damaged"
                    }
                    db[str[1]] = str[2]
                }
                "d" -> {
                    assert(str.size == 2) {
                        "Database is damaged"
                    }
                    db.remove(str[1])
                }
                else -> assert(false) {
                    "Database is damaged"
                }
            }
        }
        return db
    }

    fun rebuildDataBase() {
        cleanDataBase()
        db.forEach {
            addToFile("add ${it.key} ${it.value}\n")
        }
    }
}