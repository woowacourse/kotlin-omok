package woowacourse.omok.data.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.db.room.RoomEntity
import woowacourse.omok.data.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixture.duplicateOmokEntities
import woowacourse.omok.fixture.omokEntities
import woowacourse.omok.fixture.testContext

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao
    private lateinit var roomDao: RoomDao

    @BeforeEach
    fun setUp() {
        val dbHelper = FakeOmokSQLiteHelper(testContext)
        omokDao = OmokDao(dbHelper)
        roomDao = RoomDao(dbHelper)
    }

    @AfterEach
    fun dropDown() {
        omokDao.drop()
    }

    @DisplayName("데이터베이스에 현재 저장된 오목돌들을 가져온다")
    @Test
    fun test1() {
        // given
        roomDao.insertRoom(RoomEntity(1, "오목고수 페토의 방"))
        val entities = omokEntities
        entities.forEach { omokDao.save(it) }

        // when
        val actual = omokDao.readByRoomId(1)
        println("actual $actual")

        // then
        assertThat(actual).containsExactly(*entities)
    }

    @DisplayName("데이터 베이스에 현재 저장된 돌들을 제거한다")
    @Test
    fun test2() {
        // given
        roomDao.insertRoom(RoomEntity(1, "오목고수 페토의 방"))
        val entities = omokEntities
        entities.forEach { omokDao.save(it) }

        // when
        omokDao.drop()
        val actual = omokDao.readByRoomId(1)

        // then
        assertThat(actual).isEmpty()
    }

    @DisplayName("같은 위치에 중복 저장하지 않는다")
    @Test
    fun `test3`() {
        // given
        roomDao.insertRoom(RoomEntity(1, "오목고수 페토의 방"))
        val entities = duplicateOmokEntities
        entities.forEach { omokDao.save(it) }

        // when
        val actual = omokDao.readByRoomId(1)

        // then
        assertThat(actual).isEqualTo(listOf(duplicateOmokEntities[0]))
    }

    @DisplayName("방을 삭제하면 진행중이던 게임 데이터도 삭제된다")
    @Test
    fun test4() {
        // given
        roomDao.insertRoom(RoomEntity(1, "오목고수 페토의 방"))
        omokEntities.forEach { omokDao.save(it) }

        // when
        roomDao.deleteRoom(1)
        val actual = omokDao.readByRoomId(1)

        // then
        assertThat(actual).isEmpty()
    }
}
