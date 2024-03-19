package omok

import io.kotest.matchers.shouldBe
import omok.fixtures.createBoard
import omok.fixtures.createOmokStone
import omok.fixtures.createPoint
import org.junit.jupiter.api.Test

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
        val actual = board.last()
        // then
        actual shouldBe expected
    }

    @Test
    fun `좌표에 해당하는 오목판 위치에 돌을 놓을 수 있다`() {
        // given
        val board = createBoard()
        val point = createPoint(1, 1)
        val color = StoneColor.BLACK
        val expect = createOmokStone(1, 1, color)
        // when
        board.put(point, color)
        val actual = board[point]
        // then
        actual shouldBe expect
    }
}
