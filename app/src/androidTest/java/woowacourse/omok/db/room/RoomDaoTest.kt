package woowacourse.omok.db.room

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixture.testContext

@RunWith(AndroidJUnit4::class)
class RoomDaoTest {
    private lateinit var roomDao: RoomDao

    @BeforeEach
    fun setUp() {
        roomDao = RoomDao(FakeOmokSQLiteHelper(testContext))
    }

    @AfterEach
    fun dropDown() {
        roomDao.drop()
    }

    @DisplayName("DB에 새로운 방을 저장한다")
    @Test
    fun saveTest() {
        // given
        val entity = RoomEntity(1, roomName = "오목고수 페토의 방")

        // when
        roomDao.insertRoom(entity)

        // then
        assertThat(roomDao.getAllRooms()).contains(RoomEntity(1, "오목고수 페토의 방"))
    }

    @DisplayName("모든 방의 정보를 가져온다")
    @Test
    fun readAllTest() {
        // given
        val entities =
            arrayOf(
                RoomEntity(1, roomName = "오목 고수 페토의 방"),
                RoomEntity(2, roomName = "오목 대왕 허접 환노의 방"),
                RoomEntity(3, roomName = "오목 왕허접 크림의 방"),
                RoomEntity(4, roomName = "오목 허접 포르의 방"),
            )

        // when
        entities.forEach { roomDao.insertRoom(it) }

        // then
        assertThat(roomDao.getAllRooms()).containsExactly(*entities)
    }

    @DisplayName("특정 ID를 가진 방을 삭제한다")
    @Test
    fun deleteTest() {
        // given
        val entity = RoomEntity(1, roomName = "오목고수 페토의 방")

        // when
        roomDao.insertRoom(entity)
        roomDao.deleteRoom(1)

        // then
        assertThat(roomDao.getAllRooms()).isEmpty()
    }
}
