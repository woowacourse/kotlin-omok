package woowacourse.omok

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import omok.fixtures.createOmokStone
import omok.fixtures.createPosition
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.fixtures.createBlackBoard
import woowacourse.omok.fixtures.createBoard
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.StoneColor

class BoardTest {
    @Test
    fun `마지막으로 넣은 돌의 정보를 불러올 수 있다`() {
        // given
        val board =
            createBoard(
                createPosition(1, 1),
                createPosition(2, 2),
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
        val point = createPosition(1, 1)
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
        val position = createPosition(x, y)
        val expect = false
        // when
        val actual = board.isInRange(position)
        // then
        actual shouldBe expect
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "4:4", "5:5"], delimiter = ':')
    fun `대각선으로 연속 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        /* given
                       ●
                    ●
                 ●
              ●
           ●
         */
        val board =
            createBlackBoard(
                createPosition(1, 1),
                createPosition(2, 2),
                createPosition(3, 3),
                createPosition(4, 4),
                createPosition(5, 5),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:1", "3:1", "4:1", "5:1"], delimiter = ':')
    fun `가로로 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        /* given

           ● ● ● ● ●

         */
        val board =
            createBlackBoard(
                createPosition(1, 1),
                createPosition(2, 1),
                createPosition(3, 1),
                createPosition(4, 1),
                createPosition(5, 1),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "1:2", "1:3", "1:4", "1:5"], delimiter = ':')
    fun `세로로 연속 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        /* given

           ●
           ●
           ●
           ●
           ●

         */
        val board =
            createBlackBoard(
                createPosition(1, 1),
                createPosition(1, 2),
                createPosition(1, 3),
                createPosition(1, 4),
                createPosition(1, 5),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeTrue()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "5:5"], delimiter = ':')
    fun `5개의 돌이 연속적이지 않으면 오목이 아니다`(
        x: Int,
        y: Int,
    ) {
        /* given
                          ●
                       ●

                 ●
              ●
           ●
         */
        val board =
            createBlackBoard(
                createPosition(1, 1),
                createPosition(2, 2),
                createPosition(3, 3),
                createPosition(5, 5),
                createPosition(6, 6),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeFalse()
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "4:4 ", "5:5"], delimiter = ':')
    fun `5개의 돌의 색이 같지 않으면 오목이 아니다`(
        x: Int,
        y: Int,
    ) {
        /* given

           ● ○ ● ○ ●

         */
        val board =
            createBoard(
                createPosition(1, 1),
                createPosition(2, 1),
                createPosition(3, 1),
                createPosition(4, 1),
                createPosition(5, 1),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeFalse()
    }
}
