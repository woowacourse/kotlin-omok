package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Game2Test {
    @Test
    fun `처음에는 흑이 수를 둔다`() {
        val game = Game2(Board())

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑이 수를 둔 후에는 백이 수를 둔다`() {
        val game = Game2(Board()).apply { processTurn(Position(1, 1), Color.BLACK) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백이 수를 둔 후에는 흑이 수를 둔다`() {
        val game = Game2(Board()).apply { processTurn(Position(1, 1), Color.WHITE) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val game =
            Game2(Board()).apply {
                processTurn(Position(4, 4), Color.BLACK)
                processTurn(Position(5, 4), Color.BLACK)
                processTurn(Position(6, 5), Color.BLACK)
                processTurn(Position(5, 6), Color.BLACK)
            }

        assertThrows<IllegalArgumentException> { game.processTurn(Position(7, 4), Color.BLACK) }
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val game =
            Game2(Board()).apply {
                processTurn(Position(12, 3), Color.BLACK)
                processTurn(Position(12, 4), Color.BLACK)
                processTurn(Position(12, 7), Color.BLACK)
                processTurn(Position(12, 9), Color.BLACK)
                processTurn(Position(12, 10), Color.BLACK)
            }

        assertThrows<IllegalArgumentException> { game.processTurn(Position(12, 6), Color.BLACK) }
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val game =
            Game2(Board()).apply {
                processTurn(Position(15, 3), Color.BLACK)
                processTurn(Position(14, 3), Color.BLACK)
                processTurn(Position(12, 3), Color.BLACK)
                processTurn(Position(11, 3), Color.BLACK)
                processTurn(Position(10, 3), Color.BLACK)
            }

        assertThrows<IllegalArgumentException> { game.processTurn(Position(13, 3), Color.BLACK) }
    }
}
