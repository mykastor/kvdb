import java.io.File



data class CheckPasswordResult(val password: String?,
                               val guessed: Boolean)

// возвращает true если угадали пароль, false если нет
fun checkPassword(filename: String, input: () -> String): CheckPasswordResult {
    logger.debug {"start process of checking password."}

    val passwordHash: String
    try {
        passwordHash = getPasswordHash(filename)
    } catch (e: Exception) {
        throw e
    }

    logger.debug { "password hash is: $passwordHash" }
    if (passwordHash.toIntOrNull() == null || passwordHash.toInt() < -1) {
        throw DatabaseIsDamaged("Password hash is deleted or broken.")
    }

    val hash = passwordHash.toInt()
    if (hash == -1) {
        return CheckPasswordResult(null, true)
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
            return CheckPasswordResult(null, false)
        }
        if (hashPassword(pwd) == hash) {
            return CheckPasswordResult(pwd, true)
        } else {
            println("Wrong password. If you want to finish this process, write \"finish check\".")
        }
    }
    println("Too many attempts")
    return CheckPasswordResult(null, false)
}