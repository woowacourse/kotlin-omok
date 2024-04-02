package woowacourse.omok.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import woowacourse.omok.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixtures.context
import woowacourse.omok.fixtures.gameTurnOf

@RunWith(AndroidJUnit4::class)
class GameRecordDaoTest {
    private lateinit var dao: GameRecordDao

    @Before
    fun setUp() {
        dao = GameRecordDao(FakeOmokSQLiteHelper(context))
        dao.createTable()
    }

    @After
    fun tearDown() {
        dao.dropTable()
    }

    @Test
    @DisplayName("오목 게임 한 턴을 저장 한다")
    fun test() {
        // given
        val gameTurnEntity = gameTurnOf(x = 1, y = 1)
        val expected = 1L
        // when
        val actual = dao.saveTurn(gameTurnEntity)
        // then

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("오목 게임 기록을 저장한 후, 저장된 기록을 불러온다")
    fun test2() {
        // given
        val gameTurns =
            listOf(
                gameTurnOf(x = 1, y = 1),
                gameTurnOf(x = 1, y = 2),
            )
        val expectedIds = listOf(1L, 2L)
        val expectedTurns =
            listOf(
                gameTurnOf(x = 1, y = 1, id = 1),
                gameTurnOf(x = 1, y = 2, id = 2),
            )
        // when
        val actualIds = dao.saveGame(gameTurns)
        val actualTurns = dao.selectAll()
        // then
        assertAll(
            { assertThat(actualIds).isEqualTo(expectedIds) },
            { assertThat(actualTurns).isEqualTo(expectedTurns) },
        )
    }

    @Test
    @DisplayName("오목 게임 기록을 저장한 후, table 을 초기화 한다")
    fun test3() {
        // given
        val gameTurns =
            listOf(
                gameTurnOf(x = 1, y = 1),
                gameTurnOf(x = 1, y = 2),
            )
        dao.saveGame(gameTurns)
        val expectedDeleteCount = 2
        // when
        val id = dao.resetTable()
        // then
        id shouldBe expectedDeleteCount
    }
}
