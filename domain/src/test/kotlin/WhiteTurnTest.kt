import domain.board.PlacedBoard
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.RenjuRuleForbiddenCondition
import domain.stone.Color
import domain.stone.Position
import domain.turn.BlackTurn
import domain.turn.Turn
import domain.turn.WhiteTurn
import domain.turn.WhiteWin
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `흰 색 턴일 때 돌을 놓았는데 이겼다면 WhiteWin이 된다`() {
        val latestStone = Stone(1, 7, Color.BLACK)
        val stones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(1, 2, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(1, 3, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
            Stone(2, 4, Color.WHITE),
            latestStone
        )

        val whiteTurn: Turn =
            WhiteTurn(
                PlacedBoard(stones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        val actual = whiteTurn.addStone(Position(2, 5))
        Assertions.assertThat(actual).isInstanceOf(WhiteWin::class.java)
    }

    @Test
    fun `흰 색 턴일 때 돌을 놓았는데 이기지 못한다면 BlackTurn이 된다`() {
        val latestStone = Stone(1, 7, Color.BLACK)
        val stones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(1, 2, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(1, 3, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
            Stone(2, 4, Color.WHITE),
            latestStone
        )

        val whiteTurn: Turn =
            WhiteTurn(
                PlacedBoard(stones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        val actual = whiteTurn.addStone(Position(2, 7))
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
