package omok.model.board

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import omock.model.Position
import omok.fixtures.createBlackBlock
import omok.fixtures.createBoard
import omok.fixtures.createWhiteBlock
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokBoardTest {
    @ParameterizedTest
    @CsvSource(value = ["15:1:1", "15:7:7", "15:15:15"], delimiter = ':')
    fun `Board (1, 1) ~ (size, size) 안에만 돌을 놓을 수 있음`(
        size: Int,
        x: Int,
        y: Int,
    ) {
        shouldNotThrow<IllegalArgumentException> {
            createBoard(
                size,
                createBlackBlock(x, y),
            )
        }
    }

    @Test
    fun `마지막으로 넣은 돌의 정보를 불러올 수 있다`() {
        // given
        val board =
            createBoard(
                createBlackBlock(1, 1),
                createBlackBlock(2, 2),
            )
        val expected = createBlackBlock(x = 2, y = 2)
        // when
        val actual = board.lastBlockOrNull()
        // then
        actual shouldBe expected
    }

    @ParameterizedTest
    @CsvSource(value = ["1:1", "2:2", "3:3", "4:4", "5:5"], delimiter = ':')
    fun `오른쪽 연속 5개의 돌이 있으면 오목이다`(
        x: Int,
        y: Int,
    ) {
        // given
        val board =
            createBoard(
                createBlackBlock(1, 1),
                createBlackBlock(2, 2),
                createBlackBlock(3, 3),
                createBlackBlock(4, 4),
                createBlackBlock(5, 5),
            )
        // when
        val actual = board.isInOmok(Position(x, y))
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
            createBoard(
                createBlackBlock(1, 1),
                createBlackBlock(2, 2),
                createBlackBlock(3, 3),
                createBlackBlock(5, 5),
                createBlackBlock(6, 6),
            )
        // when
        val actual = board.isInOmok(Position(x, y))
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
                createBlackBlock(1, 1),
                createBlackBlock(2, 1),
                createBlackBlock(3, 1),
                createBlackBlock(4, 1),
                createWhiteBlock(5, 1),
            )
        // when
        val actual = board.isInOmok(Position(x, y))
        // then
        actual.shouldBeFalse()
    }
}
