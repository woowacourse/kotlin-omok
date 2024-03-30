package woowacourse.omok.model.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameRoomDaoTest {
    private lateinit var dao: GameRoomDao

    @Before
    fun setUp() {
        dao = GameRoomDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        dao.drop()
    }

    @Test
    fun saveTest() {
        val room = Room(title = "room1")
        val actual = dao.save(room)
        assertEquals(actual.title, "room1")
    }

    @Test
    fun findAllTest() {
        val actual = dao.findAll()
        assertEquals(actual, emptyList<Room>())
    }

    @Test
    fun saveAndFindAllTest() {
        val room1 = Room(title = "room1")
        val room2 = Room(title = "room2")
        dao.save(room1)
        dao.save(room2)
        val actual = dao.findAll()
        assertAll(
            { assertEquals(actual[0].title, "room1") },
            { assertEquals(actual[1].title, "room2") },
        )
    }
}
