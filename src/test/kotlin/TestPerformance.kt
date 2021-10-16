import java.io.File
import kotlin.random.Random
import kotlin.test.assertNull
import kotlin.test.*

internal class TestPerformance {

    var db = DatabaseClass("database/testdb.txt")

    @BeforeTest
    fun setUp() {
        db.cleanDataBase()
    }

    @AfterTest
    fun cleanUp() {
        File("database/testdb.txt").delete()
    }


    private fun generateRandomString() : String {
        var str = ""
        repeat (Random.nextInt(1, 10)) {
            str += if (Random.nextBoolean())
                Random.nextInt('a'.code, 'z'.code).toChar()
            else
                Random.nextInt('A'.code, 'Z'.code).toChar()
        }
        return str
    }

    @Test
    fun testPerformance() {
        val slowDB = mutableMapOf<String, String>()
        repeat(10000) {
            val key = generateRandomString()
            val value = generateRandomString()
            slowDB[key] = value
            db.add(key, value)
        }

        slowDB.forEach {
            if (Random.nextInt(0, 5) == 0) {
                db.remove(it.key)
            } else {
                assert(db.find(it.key) == it.value)
            }
        }


        assertNull( db.find("asjdlfajsdnflajsdnfkjandfkljandjfnasd"))

        db.rebuildDatabaseFile()

    }
}