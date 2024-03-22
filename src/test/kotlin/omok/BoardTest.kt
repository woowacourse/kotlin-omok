package omok

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import omok.fixtures.createBlackStone
import omok.fixtures.createBoard
import omok.fixtures.createPosition
import omok.fixtures.createWhiteStone
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BoardTest {
    @ParameterizedTest
    @CsvSource(value = ["15:16:15", "15:16:15", "15:16:16"], delimiter = ':')
    fun `Board 의 size 를 넘어가면 돌을 놓을 수 없음`(
        size: Int,
        x: Int,
        y: Int,
    ) {
        shouldThrow<IllegalArgumentException> {
            createBoard(
                size,
                createWhiteStone(x, y),
            )
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1:0", "0:1", "0:0"], delimiter = ':')
    fun `Board 에 x 가 1보다 작거나, y가 1보다 작으면 돌을 놓을 수 없음`(
        x: Int,
        y: Int,
    ) {
        shouldThrow<IllegalArgumentException> {
            createBoard(
                createWhiteStone(x, y),
            )
        }
    }

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
                createWhiteStone(x, y),
            )
        }
    }

    @Test
    fun `마지막으로 넣은 돌의 정보를 불러올 수 있다`() {
        // given
        val board =
            createBoard(
                createBlackStone(1, 1),
                createBlackStone(2, 2),
            )
        val expected = createBlackStone(x = 2, y = 2)
        // when
        val actual = board.lastOrNull()
        // then
        actual shouldBe expected
    }

    @Test
    fun `오목판에 돌을 놓으면 새로운 오목판이 만들어진다`() {
        // given
        val board = createBoard()
        // when
        val newBoard = board + createBlackStone(1, 1)
        // then
        newBoard shouldNotBe board
        newBoard shouldNotBeSameInstanceAs board
    }

    @Test
    fun `Board 의 좌표에 해당하는 곳이 빈 값이면 돌을 놓을 수 있다`() {
        // given
        val board = createBoard(createBlackStone(1, 1))
        // when
        val canPlace = board.canPlace(createWhiteStone(1, 2))
        // then
        canPlace.shouldBeTrue()
    }

    @Test
    fun `Board 의 좌표에 해당하는 곳에 이미 돌이 있으면 돌을 놓을 수 없다`() {
        // given
        val board = createBoard(createBlackStone(1, 1))
        // when
        val canPlace = board.canPlace(createWhiteStone(1, 1))
        // then
        canPlace.shouldBeFalse()
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
                createBlackStone(1, 1),
                createBlackStone(2, 2),
                createBlackStone(3, 3),
                createBlackStone(4, 4),
                createBlackStone(5, 5),
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
        // given
        val board =
            createBoard(
                createBlackStone(1, 1),
                createBlackStone(2, 2),
                createBlackStone(3, 3),
                createBlackStone(5, 5),
                createBlackStone(6, 6),
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
        // given
        val board =
            createBoard(
                createBlackStone(1, 1),
                createBlackStone(2, 1),
                createBlackStone(3, 1),
                createBlackStone(4, 1),
                createWhiteStone(5, 1),
            )
        // when
        val actual = board.isInOmok(createPosition(x, y))
        // then
        actual.shouldBeFalse()
    }
}
