import domain.board.PlacedBoard
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.RenjuRuleForbiddenCondition
import domain.stone.Color
import domain.stone.Position
import domain.turn.BlackTurn
import domain.turn.BlackWin
import domain.turn.Turn
import domain.turn.WhiteTurn
import domain.turn.WhiteWin
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `검은색 턴일 때 돌을 놓았는데 이겼다면 BlackWin이 된다`() {
        val latestStone = Stone(2, 4, Color.WHITE)
        val previousStones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(1, 2, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(1, 3, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
        )
        val nextStones = previousStones + latestStone

        val blackTurn: Turn =
            BlackTurn(
                PlacedBoard(nextStones.convertToBoard()),
                PlacedBoard(previousStones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        val actual = blackTurn.addStone(Position(1, 5))
        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `검은색 턴일 때 돌을 놓았는데 금수라면 WhiteWin이 된다`() {
        val latestStone = Stone(1, 4, Color.WHITE)
        val previousStones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(1, 1, Color.WHITE),
            Stone(4, 13, Color.BLACK),
            Stone(1, 2, Color.WHITE),
            Stone(4, 14, Color.BLACK),
            Stone(1, 3, Color.WHITE),
            Stone(5, 12, Color.BLACK),
        )
        val nextStones = previousStones + latestStone

        val blackTurn: Turn =
            BlackTurn(
                PlacedBoard(previousStones.convertToBoard()),
                PlacedBoard(nextStones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        val actual = blackTurn.addStone(Position(4, 12))
        assertThat(actual).isInstanceOf(WhiteWin::class.java)
    }

    @Test
    fun `검은색 턴일 때 돌을 놓았는데 금수도 아니고 이기지도 않았다면 WhiteTurn이 된다`() {
        val latestStone = Stone(1, 4, Color.WHITE)
        val previousStones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(1, 1, Color.WHITE),
            Stone(4, 13, Color.BLACK),
            Stone(1, 2, Color.WHITE),
            Stone(4, 14, Color.BLACK),
            Stone(1, 3, Color.WHITE),
            Stone(5, 12, Color.BLACK),
        )
        val nextStones = previousStones + latestStone
        val blackTurn: Turn =
            BlackTurn(
                PlacedBoard(previousStones.convertToBoard()),
                PlacedBoard(nextStones.convertToBoard()),
                latestStone,
                FiveStoneWinningCondition(),
                RenjuRuleForbiddenCondition()
            )
        val actual = blackTurn.addStone(Position(1, 7))
        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }
}
