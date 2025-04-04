package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.database.OmokDao
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.database.OmokEntity

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @BeforeEach
    fun setup() {
        omokDao = OmokDao(OmokDbHelper(ApplicationProvider.getApplicationContext()))
    }

    @AfterEach
    fun clean() {
        omokDao.clearAll()
    }

    @Test
    fun insertDataTest() {
        omokDao.insertData(OmokEntity(1, 1, "BLACK", "Room 1"))
        val data: List<OmokEntity> = omokDao.queryByRoomName("Room 1")
        assertThat(data.size).isEqualTo(1)
        assertThat(data.first()).isEqualTo(OmokEntity(1, 1, "BLACK", "Room 1"))
    }

    @Test
    fun queryByRoomNameTest() {
        omokDao.insertData(OmokEntity(1, 1, "BLACK", "Room 1"))
        omokDao.insertData(OmokEntity(15, 15, "WHITE", "Room 2"))
        val data: List<OmokEntity> = omokDao.queryByRoomName("Room 1")
        assertThat(data.size).isEqualTo(1)
        assertThat(data).isEqualTo(listOf(OmokEntity(1, 1, "BLACK", "Room 1")))
    }

    @Test
    fun clearDataByRoomNameTest() {
        omokDao.insertData(OmokEntity(1, 1, "BLACK", "Room 1"))
        omokDao.insertData(OmokEntity(15, 15, "WHITE", "Room 2"))
        omokDao.clearRoom("Room 1")
        assertThat(omokDao.queryByRoomName("Room 1").size).isEqualTo(0)
    }
}
