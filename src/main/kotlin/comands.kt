import java.io.File

fun add(db : DatabaseClass, str : List<String>) {
    if (str.size != 3) {
        println("Please, enter input format in this format: \"add key value\".\nNotice that key and value cannot have whitespaces")
        return
    }
    db.add(str[1], str[2])
}

fun remove(db : DatabaseClass, str : List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"remove key\".\nNotice that key cannot have whitespaces")
        return
    }
    db.remove(str[1])
}

fun find(db : DatabaseClass, str : List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"find key\".\nNotice that key cannot have whitespaces")
        return
    }
    val res = db.find(str[1])
    if (res == null) println("There is no such element")
    else println(res)
}

fun setNewPassword(db : DatabaseClass, str: List<String>) {
    if (str.size != 2) {
        println("Please, enter input format in this format: \"find key\".\nNotice that password cannot have whitespaces")
        return
    }
    db.setNewPassword(str[1])
}

fun printAllDatabaseNames() {
    val names = getAllDatabaseNames()
    if (names.isEmpty()) {
        println("Now there is no database available")
    } else {
        print("databases names:")
        names.forEach { it ->
            if (it == names.first()) {
                print(" $it")
            } else {
                print(", $it")
            }
        }
        println()
    }
}

fun help() {
    println("Hello! It's a key-value database. Command you can use:")

    println("add KEY VALUE - adds an element to the database, if it already exists, replaces it with a new value.  \" +\n" +
            "find KEY - find an element by key.\n" +
            "remove KEY - remove an element by key, if there is no such element nothing will happen.\n" +
            "clean!! - clean the database.\n" +
            "path? - print the current path to database\n" +
            "set PASSWOR - set password\n" +
            "all - print first 100 elements in database\n" +
            "rmpwd - remove password\n" +
            "create DB_NAME - create a new database with DB_NAME\n" +
            "choose DB_NAME\n" +
            "out - get out from current database\n" +
            "Notice that KEY, VALUE and PASSWORD are strings without whitespaces")
}

fun createNewDatabase(filename: String) : DatabaseClass? {
    if (getPath(filename) != null) {
        println("Cannot create a new database because name $filename is used")
        return null
    }
    try {
        return DatabaseClass(getNewPath(filename))
    } catch (e : Exception) {
        throw e
    }
}

fun getDatabase(filename: String) : DatabaseClass? {
    val path = getPath(filename)
    if (path == null) {
        println("There is no such database")
        return null
    }
    try {
        return DatabaseClass(path)
    } catch (e: Exception) {
        throw e
    }
}
