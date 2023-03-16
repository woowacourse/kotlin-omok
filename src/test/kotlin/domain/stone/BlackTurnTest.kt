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
        val stone: Stone = Stone(StonePosition.from(2, 1)!!, StoneType.BLACK)
        board.putStone(stone)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is BlackTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `stone를 추가한 후 WhiteTurn를 반환`() {
        val board: Board = Board()
        val stone: Stone = Stone(StonePosition.from(2, 1)!!, StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is WhiteTurn,
        ).isEqualTo(true)
    }

    @Test
    fun `오목 조건 충족하면 End 상태로 Black이 Win`() {
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 2)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 3)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 4)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 5)!!, StoneType.BLACK)

        val blackTurn: BlackTurn = BlackTurn(board)

        assertThat(
            blackTurn.put(stone) is End,
        ).isEqualTo(true)
    }
}
