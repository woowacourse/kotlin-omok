package woowacourse.omok.db.omok

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.db.room.RoomDao
import woowacourse.omok.db.room.RoomDaoMapper
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.room.Room
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixture.testContext

@RunWith(AndroidJUnit4::class)
class OmokDaoMapperTest {
    private lateinit var omokDao: OmokDao
    private lateinit var roomDao: RoomDao
    private lateinit var omokDaoService: OmokDaoMapper
    private lateinit var roomDaoService: RoomDaoMapper

    @BeforeEach
    fun setUp() {
        val dbHelper = FakeOmokSQLiteHelper(testContext)
        omokDao = OmokDao(dbHelper)
        roomDao = RoomDao(dbHelper)

        omokDaoService = OmokDaoMapper(omokDao)
        roomDaoService = RoomDaoMapper(roomDao)
    }

    @AfterEach
    fun dropDown() {
        omokDao.drop()
    }

    @DisplayName("데이터베이스에 돌들을 저장하고 저장된 오목돌들을 가져온다")
    @Test
    fun saveAndReadAllPointTest() {
        // given
        roomDaoService.save(Room(roomName = "오목 고수 페토의 방"))
        val points =
            arrayOf(
                Point(Column(1), Row(1), BoardStatus.Moved(StoneColor.BLACK)),
                Point(Column(2), Row(2), BoardStatus.Moved(StoneColor.WHITE)),
                Point(Column(3), Row(3), BoardStatus.Moved(StoneColor.BLACK)),
            )
        points.forEach { omokDaoService.saveNewPoint(it, 1) }

        // when
        val actual = omokDaoService.readAllPoint(1)

        // then
        assertThat(actual).containsExactly(*points)
    }

    @DisplayName("데이터 베이스에 현재 저장된 돌들을 제거한다")
    @Test
    fun test2() {
        // given
        omokDaoService
            .saveNewPoint(
                Point(
                    Column(1),
                    Row(1),
                    BoardStatus.Moved(StoneColor.BLACK),
                ),
                1,
            )

        // when
        omokDaoService.drop()
        val actual = omokDaoService.readAllPoint(1)

        // then
        assertThat(actual).isEmpty()
    }
}
