import domain.board.PlacedBoard
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.RenjuRuleForbiddenCondition
import domain.stone.Color
import domain.turn.WhiteWin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException

class WhiteWinTest {

    @Test
    fun `흰 돌이 이기지 않았으면 예외가 발생한다`() {
        val latestStone = Stone(2, 4, Color.WHITE)
        val stones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(1, 2, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(1, 3, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK)
        )
        val nextBoard = PlacedBoard((stones + latestStone).convertToBoard())

        assertThrows<IllegalStateException> {
            WhiteWin(
                nextBoard,
                PlacedBoard(stones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        }
    }
}
