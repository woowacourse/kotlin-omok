package woowacourse.omok.model.database

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class GameRoomDaoTest {
    private lateinit var dao: GameRoomDao

    @BeforeEach
    fun setUp() {
        dao = GameRoomDao(ApplicationProvider.getApplicationContext())
    }

    @AfterEach
    fun tearDown() {
        dao.drop()
    }

    @Test
    fun saveTest() {
        val room = Room(title = "room1")
        val actual = dao.save(room)
        assertThat(actual.title).isEqualTo("room1")
    }

    @Test
    fun findAllTest() {
        val actual = dao.findAll()
        assertThat(actual).isEmpty()
    }

    @Test
    fun saveAndFindAllTest() {
        val room1 = Room(title = "room1")
        val room2 = Room(title = "room2")
        dao.save(room1)
        dao.save(room2)
        val actual = dao.findAll()
        assertAll(
            { assertThat(actual[0].title).isEqualTo("room1") },
            { assertThat(actual[1].title).isEqualTo("room2") },
        )
    }
}
