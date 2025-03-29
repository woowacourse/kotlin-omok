package woowacourse.omok.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.fake.FakeOmokSQLiteHelper
import woowacourse.omok.domain.repository.RoomRepository
import woowacourse.omok.domain.room.Room
import woowacourse.omok.fixture.testContext

@RunWith(AndroidJUnit4::class)
class RoomRepositoryImplTest {
    private lateinit var roomDao: RoomDao
    private lateinit var roomRepository: RoomRepository

    @BeforeEach
    fun setUp() {
        roomDao = RoomDao(FakeOmokSQLiteHelper(testContext))
        roomRepository = RoomRepositoryImpl(roomDao)
    }

    @AfterEach
    fun dropDown() {
        roomDao.drop()
    }

    @DisplayName("데이터베이스에 방을 저장하고 저장된 방을 모두 가져온다")
    @Test
    fun saveAndReadAllRoomTest() {
        val room =
            arrayOf(
                Room(1, roomName = "오목 고수 페토의 방"),
                Room(2, roomName = "오목 대왕 허접 환노의 방"),
                Room(3, roomName = "오목 왕허접 크림의 방"),
                Room(4, roomName = "오목 허접 포르의 방"),
            )

        room.forEach { roomRepository.save(it) }

        val actual = roomRepository.readAll()
        assertThat(actual).containsExactly(*room)
    }

    @DisplayName("특정 ID를 가진 방을 삭제한다")
    @Test
    fun deleteTest() {
        // given
        val room = Room(roomName = "오목고수 페토의 방")

        // when
        roomRepository.save(room)
        roomRepository.delete(1)

        // then
        assertThat(roomRepository.readAll()).isEmpty()
    }
}
