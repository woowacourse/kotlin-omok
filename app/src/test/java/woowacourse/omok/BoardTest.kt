import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Duplicated
import woowacourse.omok.model.board.Full
import woowacourse.omok.model.board.OutOfRange
import woowacourse.omok.model.board.Success
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor

class BoardTest {
    @Test
    fun `중복된 위치에 돌을 놓을경우 중복되었다고 알린다`() {
        val point = Point(1, 1)
        val firstBoard = Board()
        val stone = Stone(point, StoneColor.WHITE)
        val secondBoard = firstBoard.place(stone) as Success
        val actual = secondBoard.board.place(stone)
        assertThat(actual).isInstanceOf(Duplicated::class.java)
    }

    @Test
    fun `오목판이 가득찼을때 가득 찼다고 알린다`() {
        val stones = mutableSetOf<Stone>()
        val board = Board(stones)

        for (i in 1..15) {
            for (j in 1..15) {
                stones.add(Stone(Point(i, j), StoneColor.WHITE))
            }
        }

        val stone = Stone(Point(1, 1), StoneColor.WHITE)
        val boardState = board.place(stone)
        assertThat(boardState).isInstanceOf(Full::class.java)
    }

    @Test
    fun `Stone이 좌표 범위 밖을 벗어났을 경우 범위를 벗어났다고 알린다`() {
        val point = Point(-1, -1)
        val board = Board()
        val stone = Stone(point, StoneColor.WHITE)
        val actual = board.place(stone)
        assertThat(actual).isInstanceOf(OutOfRange::class.java)
    }
}
