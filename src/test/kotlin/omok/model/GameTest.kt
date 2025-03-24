package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `처음에는 흑이 수를 둔다`() {
        val game = Game(Board(), Rule())

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑이 수를 둔 후에는 백이 수를 둔다`() {
        val game = Game(Board(), Rule()).apply { processTurn(Position(1, 1), Color.BLACK) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백이 수를 둔 후에는 흑이 수를 둔다`() {
        val game = Game(Board(), Rule()).apply { processTurn(Position(1, 1), Color.WHITE) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 1`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(1, 1), Color.WHITE)
                processTurn(Position(1, 2), Color.WHITE)
                processTurn(Position(1, 3), Color.WHITE)
                processTurn(Position(1, 4), Color.WHITE)
            }

        val actual: MoveResult = game.processTurn(Position(1, 5), Color.WHITE)
        val expected: MoveResult = MoveResult.Success.WhiteWin

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 2`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(13, 5), Color.BLACK)
                processTurn(Position(12, 6), Color.BLACK)
                processTurn(Position(11, 7), Color.BLACK)
                processTurn(Position(10, 8), Color.BLACK)
            }

        val actual: MoveResult = game.processTurn(Position(9, 9), Color.BLACK)
        val expected: MoveResult = MoveResult.Success.BlackWin

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되지 않았으면 게임이 진행 중이다`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(8, 8), Color.WHITE)
                processTurn(Position(8, 9), Color.WHITE)
                processTurn(Position(9, 8), Color.WHITE)
            }

        val actual: MoveResult = game.processTurn(Position(9, 9), Color.WHITE)
        val expected: MoveResult = MoveResult.Success.Playing

        assertThat(actual).isEqualTo(expected)
    }
}
