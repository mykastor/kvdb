import java.io.File

const val BOUND_TO_REBUILD = 1e5

typealias DataBase = MutableMap<String, String>

data class DataBaseClass(val PATH_TO_DB : String) {

    private val db = loadDataBase()

    fun addToFile(str: String) {
        File(PATH_TO_DB).appendText(str)
    }

    private fun initDataBase() {
        if (!File(PATH_TO_DB).exists()) {
            File(PATH_TO_DB).appendText("init database123\n")
        }
    }

    fun removeDataBase() {
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
        addToFile("add $key $value\n")
    }

    fun remove(key : String) {
        if (db.containsKey(key)) {
            db.remove(key)
            addToFile("delete $key\n")
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
                "add" -> {
                    db[str[1]] = str[2]
                }
                "delete" -> {
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

    private fun rebuildDataBase() {
        // TODO
    }
}

fun main(args: Array<String>) {
    val db = DataBaseClass("database/db.txt")

    while (true) {
        val cmd = readLine() ?: return
        val str = cmd.split(' ')
        when (str[0]) {
            "add" -> {
                if (str.size != 3) {
                    println("Please, enter input format in this format: \"add key value\".\nNotice that key and value cannot have a whitespace")
                    continue
                }
                db.add(str[1], str[2])
            }
            "remove" -> {
                if (str.size != 2) {
                    println("Please, enter input format in this format: \"remove key\".\nNotice that key cannot have a whitespace")
                    continue
                }
                db.remove(str[1])
            }
        }
    }
}
