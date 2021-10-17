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

fun help() {
    // TODO обновить help и README.md
    println("Hello! It's a key-value database. Command you can use:")
    println("[add key value] - adds an element to the database, if it already exists, replaces old value with new")
    println("[find key] - find an element by key")
    println("[remove key] - remove an element by key, if there is no such element nothing will happen")
    println("[clean!!] - clean the database")
    println("[changepath path] - change the path to database")
    println("[path?] - print the current path to database")
    println("Notice that [key] and [value] are strings without whitespaces")
}
