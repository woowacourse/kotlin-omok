package domain.stone

import domain.state.BlackTurn
import domain.state.End
import domain.state.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {

    @Test
    fun `stone을 추가할 수 없는 상태라면 추가하지 않고 BlackTurn을 반환`() {
        val board: Board = Board()
        val stone: Stone = Stone(StonePosition.from(2, 1), StoneType.BLACK)
        board.putStone(stone)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is BlackTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `stone를 추가한 후 WhiteTurn를 반환`() {
        val board: Board = Board()
        val stone: Stone = Stone(StonePosition.from(2, 1), StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is WhiteTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `오목 조건 충족하면 End 상태로 Black이 Win`() {
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(1, 1), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 2), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 4), StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 5), StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is End,
        ).isEqualTo(true)
    }

    @Test
    fun `블랙돌을 놓으려는 위치가 장목이면 BlackTurn을 반환`() {
        val board: Board = Board()

        board.putStone(Stone(StonePosition.from(1, 1), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 2), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 4), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(6, 6), StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 5), StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is BlackTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `블랙돌을 놓으려는 위치가 3-3이면 BlackTurn을 반환`() {
        val board: Board = Board()

        board.putStone(Stone(StonePosition.from(1, 1), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 2), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 1), StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(2, 1), StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is BlackTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `블랙돌을 놓으려는 위치가 4-4이면 BlackTurn을 반환`() {
        val board: Board = Board()

        board.putStone(Stone(StonePosition.from(15, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(14, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(12, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(11, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(10, 3), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(12, 4), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(12, 7), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(12, 9), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(12, 10), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(9, 10), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(8, 10), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(6, 10), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(8, 11), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(8, 8), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(7, 8), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(6, 8), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(6, 5), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 5), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 6), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 7), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 7), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 4), StoneType.WHITE))
        board.putStone(Stone(StonePosition.from(9, 8), StoneType.WHITE))

        val stone: Stone = Stone(StonePosition.from(8, 3), StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is BlackTurn,
        ).isEqualTo(true)
    }
}
