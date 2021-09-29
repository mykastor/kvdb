import java.io.File

typealias DataBase = MutableMap<String, String>
const val PATH_TO_DB = "database/db.txt"
const val BOUND_TO_REBUILD = 1e5

data class LoadDataBaseResult(val db : DataBase,
                              val needToRebuild: Boolean)

fun initDataBase() {
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

fun loadDataBase() : LoadDataBaseResult {
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
    return LoadDataBaseResult(db, deleteCount >= BOUND_TO_REBUILD)
}

fun rebuildDataBase(db : DataBase) {
    // TODO
}

fun main(args: Array<String>) {
    initDataBase()
    removeDataBase()
    val (db, needToRebuild) = loadDataBase()
    if (needToRebuild) {
        println("do you want to rebuild database? (it might take some time) [y; n]")
        println("it happened because the program did not close with the \"exit\" command")
        val str = readLine() ?: return
        if (str.first().lowercase() == "y") {
            rebuildDataBase(db)
        }
    }
    /*
    while (true) {
        val str = readLine() ?: return
    }
     */
}
