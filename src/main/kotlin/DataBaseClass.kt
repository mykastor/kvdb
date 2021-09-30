import java.io.File

const val PATH_TO_DATABASE = "database/db.txt"

const val BOUND_TO_REBUILD = 1e5

typealias DataBase = MutableMap<String, String>

data class DataBaseClass(val PATH_TO_DB : String) {

    private val db = loadDataBase()

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

    private fun askToRebuild() {
        println("do you want to rebuild database? (it might take some time) [y; n]")
        println("it happened because the program did not close with the \"exit\" command")
        val str = readLine() ?: return
        if (str.first().lowercase() == "y") {
            rebuildDataBase()
        }
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
        var deleteCount = 0
        lines.forEach {
            val str = it.split(' ')
            when (str[0]) {
                "a" -> {
                    db[str[1]] = str[2]
                }
                "d" -> {
                    deleteCount += 1
                    db.remove(str[1])
                }
            }
        }
        if (deleteCount >= BOUND_TO_REBUILD) {
            askToRebuild()
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