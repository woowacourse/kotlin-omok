package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameTest {
    @Test
    fun `처음에는 흑이 수를 둔다`() {
        val game = Game(Board())

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑이 수를 둔 후에는 백이 수를 둔다`() {
        val game = Game(Board()).apply { processTurn(Position(1, 1), Color.BLACK) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백이 수를 둔 후에는 흑이 수를 둔다`() {
        val game = Game(Board()).apply { processTurn(Position(1, 1), Color.WHITE) }

        val actual: Color = game.chooseTurn()
        val expected: Color = Color.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 1`() {
        val game =
            Game(Board()).apply {
                processTurn(Position(1, 1), Color.WHITE)
                processTurn(Position(1, 2), Color.WHITE)
                processTurn(Position(1, 3), Color.WHITE)
                processTurn(Position(1, 4), Color.WHITE)
            }

        val actual: GameState = game.processTurn(Position(1, 5), Color.WHITE)
        val expected: GameState = GameState.FINISHED

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되면 게임이 종료된다 2`() {
        val game =
            Game(Board()).apply {
                processTurn(Position(13, 5), Color.WHITE)
                processTurn(Position(12, 6), Color.WHITE)
                processTurn(Position(11, 7), Color.WHITE)
                processTurn(Position(10, 8), Color.WHITE)
            }

        val actual: GameState = game.processTurn(Position(9, 9), Color.WHITE)
        val expected: GameState = GameState.FINISHED

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되지 않았으면 게임이 진행 중이다`() {
        val game =
            Game(Board()).apply {
                processTurn(Position(8, 8), Color.WHITE)
                processTurn(Position(8, 9), Color.WHITE)
                processTurn(Position(9, 8), Color.WHITE)
            }

        val actual: GameState = game.processTurn(Position(9, 9), Color.WHITE)
        val expected: GameState = GameState.PLAYING

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val game =
            Game(Board()).apply {
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
            Game(Board()).apply {
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
            Game(Board()).apply {
                processTurn(Position(15, 3), Color.BLACK)
                processTurn(Position(14, 3), Color.BLACK)
                processTurn(Position(12, 3), Color.BLACK)
                processTurn(Position(11, 3), Color.BLACK)
                processTurn(Position(10, 3), Color.BLACK)
            }

        assertThrows<IllegalArgumentException> { game.processTurn(Position(13, 3), Color.BLACK) }
    }
}
