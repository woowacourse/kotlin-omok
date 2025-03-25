package omok.model

import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
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
        val game = Game(Board(), Rule()).apply { processTurn(Position(Col(1), Row(1)), Color.BLACK) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백이 수를 둔 후에는 흑이 수를 둔다`() {
        val game = Game(Board(), Rule()).apply { processTurn(Position(Col(1), Row(1)), Color.WHITE) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 1`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(Col(1), Row(1)), Color.WHITE)
                processTurn(Position(Col(1), Row(2)), Color.WHITE)
                processTurn(Position(Col(1), Row(3)), Color.WHITE)
                processTurn(Position(Col(1), Row(4)), Color.WHITE)
            }

        val actual: MoveResult = game.processTurn(Position(Col(1), Row(5)), Color.WHITE)
        val expectedClass = MoveResult.Success.Finished::class.java
        val expectedColor: Color = Color.WHITE

        assertThat(actual).isInstanceOfSatisfying(expectedClass) { moveResult ->
            assertThat(moveResult.winner).isEqualTo(expectedColor)
        }
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 2`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(Col(13), Row(5)), Color.BLACK)
                processTurn(Position(Col(12), Row(6)), Color.BLACK)
                processTurn(Position(Col(11), Row(7)), Color.BLACK)
                processTurn(Position(Col(10), Row(8)), Color.BLACK)
            }

        val actual: MoveResult = game.processTurn(Position(Col(9), Row(9)), Color.BLACK)
        val expectedClass = MoveResult.Success.Finished::class.java
        val expectedColor: Color = Color.BLACK

        assertThat(actual).isInstanceOfSatisfying(expectedClass) { finished ->
            assertThat(finished.winner).isEqualTo(expectedColor)
        }
    }

    @Test
    fun `오목이 완성되지 않았으면 게임이 진행 중이다`() {
        val game =
            Game(Board(), Rule()).apply {
                processTurn(Position(Col(8), Row(8)), Color.WHITE)
                processTurn(Position(Col(8), Row(9)), Color.WHITE)
                processTurn(Position(Col(9), Row(8)), Color.WHITE)
            }

        val actual: MoveResult = game.processTurn(Position(Col(9), Row(9)), Color.WHITE)
        val expected: MoveResult = MoveResult.Success.Playing

        assertThat(actual).isEqualTo(expected)
    }
}
