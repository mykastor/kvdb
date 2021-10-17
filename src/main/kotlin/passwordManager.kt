import java.io.File

// in this password manager hash function is super simple

fun hashByKey(str: String, key: String?) : String {
    if (key == null) return str
    if (key.isEmpty()) {
        logger.info { "empty key is used in hashing"}
        return str
    }
    val builder = StringBuilder()
    for (i in str.indices) {
        val c = key[i % key.length]
        if (i > 0)
            builder.append("#")
        builder.append((str[i].code + c.code).toString())
    }
    return builder.toString()
}

fun unleashByKey(str: String, key: String?) : String {
    if (key == null) return str
    if (key.isEmpty()) {
        logger.info { "empty key is used in hashing"}
        return str
    }
    val builder = StringBuilder()
    val numbers = str.split("#")
    for(i in numbers.indices) {
        if (numbers[i].toIntOrNull() == null) {
            throw DatabaseIsDamaged(str)
        }
        builder.append((numbers[i].toInt() - key[i % key.length].code).toChar())
    }
    return builder.toString()
}

private const val mod: Long = (1e9 + 7).toLong()
private const val hashPower: Long = 1337

fun hashPassword(str: String?) : Int {
    if (str == null) {
        return -1
    }
    var res: Long = 0
    str.forEach {
        res = (res * hashPower + it.code) % mod
    }
    logger.info { "hashed password is: $res"}
    return res.toInt()
}

fun getPasswordHash(filename: String) : String {
    if (!File(filename).exists()) {
        throw NoSuchFile(filename)
    }
    val lines = File(filename).readLines()
    if (lines.isEmpty()) {
        throw DatabaseIsDamaged("file is empty")
    }
    return lines[0]
}

data class checkPasswordResult(val password: String?,
                                val guessed: Boolean)

// возвращает true если угадали пароль, false если нет
fun checkPassword(filename: String, input: () -> String): checkPasswordResult {
    logger.info {"start process of checking password"}

    val passwordHash: String
    try {
        passwordHash = getPasswordHash(filename)
    } catch (e: Exception) {
        throw e
    }

    logger.info { "password hash is: $passwordHash" }
    if (passwordHash.toIntOrNull() == null || passwordHash.toInt() < -1) {
        throw DatabaseIsDamaged("Password hash is deleted or broken")
    }

    val hash = passwordHash.toInt()
    if (hash == -1) {
        return checkPasswordResult(null, true)
    }

    println("This database is secured by a password. Please, enter a password:")
    repeat(3) {
        val pwd: String
        try {
            pwd = input()
        } catch (e: Exception) {
            throw e
        }
        if (pwd == "finish check") {
            return checkPasswordResult(null, false)
        }
        if (hashPassword(pwd) == hash) {
            return checkPasswordResult(pwd, true)
        } else {
            println("Wrong password. If you want to finish this process, write \"finish check\"")
        }
    }
    println("Too many attempts")
    return checkPasswordResult(null, false)
}