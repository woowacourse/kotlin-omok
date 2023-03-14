package turn

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `2, 3에 착수하면 state의 1, 2 위치가 true다`() {
        val blackTurn = BlackTurn()
        blackTurn.move(2, 3)

        assertThat(blackTurn.state[1][2]).isEqualTo(true)
    }

    @Test
    fun `2, 2에 착수하면 state의 1, 1 위치가 true다`() {
        val blackTurn = BlackTurn()
        blackTurn.move(2, 2)

        assertThat(blackTurn.state[1][1]).isEqualTo(true)
    }
}
