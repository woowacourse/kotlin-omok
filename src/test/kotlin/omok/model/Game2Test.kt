package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Game2Test {
    @Test
    fun `처음에는 흑이 수를 둔다`() {
        val game = Game2(Board()).apply { processTurn(Position(1, 1)) }

        val actual: Color? = game.board.lastStone?.color
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑이 수를 둔 후에는 백이 수를 둔다`() {
        val game =
            Game2(Board()).apply {
                processTurn(Position(1, 1))
                processTurn(Position(1, 2))
            }

        val actual: Color? = game.board.lastStone?.color
        val expected: Color = Color.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백이 수를 둔 후에는 흑이 수를 둔다`() {
        val game =
            Game2(Board()).apply {
                processTurn(Position(1, 1))
                processTurn(Position(1, 2))
                processTurn(Position(1, 3))
            }

        val actual: Color? = game.board.lastStone?.color
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }
}
