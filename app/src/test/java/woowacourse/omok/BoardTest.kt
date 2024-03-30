package omok

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import omok.fixtures.createBlackBoard
import omok.fixtures.createBoard
import omok.fixtures.createOmokStone
import omok.fixtures.createPoint
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.StoneColor

class BoardTest {
    @Test
    fun `마지막으로 넣은 돌의 정보를 불러올 수 있다`() {
        // given
        val board =
            createBoard(
                createPoint(1, 1),
                createPoint(2, 2),
            )
        val expected = createOmokStone(x = 2, y = 2, color = StoneColor.WHITE)
        // when
        val actual = board.lastStone
        // then
        actual shouldBe expected
    }

    @Test
    fun `오목판 범위 내 좌표에 돌을 추가할 수 있다`() {
        // given
        val board = createBoard()
        val point = createPoint(1, 1)
        val color = StoneColor.BLACK
        val expect = createOmokStone(1, 1, color)
        // when
        val newBoard = board + OmokStone(point, color)
        val actual = newBoard[point]
        // then
        actual shouldBe expect
    }

    @ParameterizedTest
    @CsvSource(value = ["0:0", "16:16", "0:16", "16:0"], delimiter = ':')
    fun `오목판의 범위를 벗어나는 위치에 돌을 놓을 수 없다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board = createBoard()
        val newStone = OmokStone(createPoint(x, y), StoneColor.BLACK)
        val expect = false
        // when
        val actual = board.isInRange(newStone)
        // then
        actual shouldBe expect
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "4:4", "5:5"], delimiter = ':')
    fun `대각선으로 연속 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBlackBoard(
                createPoint(1, 1),
                createPoint(2, 2),
                createPoint(3, 3),
                createPoint(4, 4),
                createPoint(5, 5),
            )
        // when
        val actual = board.isInOmok(createPoint(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:1", "3:1", "4:1", "5:1"], delimiter = ':')
    fun `가로로 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBlackBoard(
                createPoint(1, 1),
                createPoint(2, 1),
                createPoint(3, 1),
                createPoint(4, 1),
                createPoint(5, 1),
            )
        // when
        val actual = board.isInOmok(createPoint(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "1:2", "1:3", "1:4", "1:5"], delimiter = ':')
    fun `세로로 연속 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBlackBoard(
                createPoint(1, 1),
                createPoint(1, 2),
                createPoint(1, 3),
                createPoint(1, 4),
                createPoint(1, 5),
            )
        // when
        val actual = board.isInOmok(createPoint(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "5:5"], delimiter = ':')
    fun `5개의 돌이 연속적이지 않으면 오목이 아니다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBlackBoard(
                createPoint(1, 1),
                createPoint(2, 2),
                createPoint(3, 3),
                createPoint(5, 5),
                createPoint(6, 6),
            )
        // when
        val actual = board.isInOmok(createPoint(x, y))
        // then
        actual.shouldBeFalse()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "4:4 ", "5:5"], delimiter = ':')
    fun `5개의 돌의 색이 같지 않으면 오목이 아니다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBoard(
                createPoint(1, 1),
                createPoint(2, 1),
                createPoint(3, 1),
                createPoint(4, 1),
                createPoint(5, 1),
            )
        // when
        val actual = board.isInOmok(createPoint(x, y))
        // then
        actual.shouldBeFalse()
    }
}
