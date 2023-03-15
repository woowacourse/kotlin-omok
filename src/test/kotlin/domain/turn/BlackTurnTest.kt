package domain.turn

import domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `2, 3에 착수하면 state의 2, 3 위치가 true다`() {
        val blackTurn = BlackTurn()
        blackTurn.move(Stone(2, 3))

        assertThat(blackTurn.state.value[2][3]).isTrue
    }

    @Test
    fun `2, 2에 착수하면 state의 2, 2 위치가 true다`() {
        val blackTurn = BlackTurn()
        blackTurn.move(Stone(2, 2))

        assertThat(blackTurn.state.value[2][2]).isTrue
    }
}
