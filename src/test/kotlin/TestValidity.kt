import java.io.File
import kotlin.random.Random
import kotlin.test.*

internal class TestValidity {

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
        db.add("1", "1")
        db.add("aZ#1%23", "zSAJd13812#%")
    }

    @Test
    fun testFind() {
        val slowDB = mutableMapOf<String, String>()
        repeat(100) {
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

    @Test
    fun testPassword() {
        db.setNewPassword("kek123")
        assertEquals(true, checkPassword(db.pathToDatabase) { "kek123" }.guessed )
        assertEquals(false, checkPassword(db.pathToDatabase) { "kek124"}.guessed )
        assertEquals(false, checkPassword(db.pathToDatabase) { "" }.guessed )
        db.setNewPassword("kek124")
        assertEquals(true, checkPassword(db.pathToDatabase) { "kek124" }.guessed)
        assertEquals("kek124", checkPassword(db.pathToDatabase) { "kek124" }.password)
        assertEquals(false, checkPassword(db.pathToDatabase) { "kek123" }.guessed )
        assertFailsWith<NoSuchFile> { checkPassword("asdjfs;dfsdf") { "" }.guessed  }
    }

}
