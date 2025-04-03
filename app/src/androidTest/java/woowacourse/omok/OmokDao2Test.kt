package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.database.OmokDao2
import woowacourse.omok.database.OmokDbHelper2
import woowacourse.omok.database.OmokEntity2

@RunWith(AndroidJUnit4::class)
class OmokDao2Test {
    private lateinit var omokDao: OmokDao2

    @BeforeEach
    fun setup() {
        omokDao = OmokDao2(OmokDbHelper2(ApplicationProvider.getApplicationContext()))
    }

    @AfterEach
    fun clean() {
        omokDao.clearAll()
    }

    @Test
    fun insertDataTest() {
        omokDao.insertData(OmokEntity2(1, 1, "BLACK", "Room 1"))
        val data: List<OmokEntity2> = omokDao.queryByRoomName("Room 1")
        assertThat(data.size).isEqualTo(1)
        assertThat(data.first()).isEqualTo(OmokEntity2(1, 1, "BLACK", "Room 1"))
    }

    @Test
    fun queryByRoomNameTest() {
        omokDao.insertData(OmokEntity2(1, 1, "BLACK", "Room 1"))
        omokDao.insertData(OmokEntity2(15, 15, "WHITE", "Room 2"))
        val data: List<OmokEntity2> = omokDao.queryByRoomName("Room 1")
        assertThat(data.size).isEqualTo(1)
        assertThat(data).isEqualTo(listOf(OmokEntity2(1, 1, "BLACK", "Room 1")))
    }

    @Test
    fun clearDataByRoomNameTest() {
        omokDao.insertData(OmokEntity2(1, 1, "BLACK", "Room 1"))
        omokDao.insertData(OmokEntity2(15, 15, "WHITE", "Room 2"))
        omokDao.clearRoom("Room 1")
        assertThat(omokDao.queryByRoomName("Room 1").size).isEqualTo(0)
    }
}
