import java.io.File
import kotlin.random.Random
import kotlin.test.*

internal class TestValidity {

    var db = DataBaseClass("database/testdb.txt")

    @BeforeTest
    fun setUp() {
        db.cleanDataBase()
    }

    private fun generateRandomString() : String {
        var str = ""
        repeat (Random.nextInt(1, 100)) {
            str += if (Random.nextBoolean())
                Random.nextInt('a'.code, 'z'.code).toChar()
            else
                Random.nextInt('A'.code, 'Z'.code).toChar()
        }
        return str
    }

    @Test
    fun testAdd() {
        val keys = mutableSetOf<String>()
        repeat(100) {
            val key = generateRandomString()
            val value = generateRandomString()
            keys.add(key)
            db.add(key, value)
        }
    }

    @Test
    fun testFind() {
        val slowDB = mutableListOf<Pair<String, String>>()
        repeat(100) {
            val key = generateRandomString()
            val value = generateRandomString()
            slowDB.add(Pair(key, value))
            db.add(key, value)
        }

        repeat(100) {
            val id = Random.nextInt(0, slowDB.size - 1)
            assertEquals(db.find(slowDB[id].first), slowDB[id].second)
        }

        assertEquals(null, db.find("asjdlfajsdnflajsdnfkjandfkljandjfnasd"))

    }

    @Test
    fun testRemove() {
        db.add("a", "b")
        db.add("b", "c")
        db.add("a", "d")
        db.remove("e")
        db.remove("a")
        assertNull( db.find("a"))
        assertEquals("c", db.find("b"))

    }

}
