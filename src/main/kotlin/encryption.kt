import java.io.File

fun hashByKey(str: String, key: String?) : String {
    if (key == null) return str
    if (key.isEmpty()) {
        logger.debug { "empty key is used in hashing"}
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
        logger.debug { "empty key is used in hashing"}
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
    logger.debug { "hashed password is: $res"}
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