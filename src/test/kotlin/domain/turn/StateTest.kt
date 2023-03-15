package domain.turn

import domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTest {
    @Test
    fun `2, 3에 착수하면 state의 2, 3 위치가 true다`() {
        val state = State()
        state.move(Stone(2, 3))

        assertThat(state.value[2][3]).isEqualTo(true)
    }

    @Test
    fun `2, 2에 착수하면 state의 2, 2 위치가 true다`() {
        val state = State()
        state.move(Stone(2, 2))

        assertThat(state.value[2][2]).isEqualTo(true)
    }

    @Test
    fun `1, 1에 돌이 있으면 둘 수 있는 상태는 false다`() {
        val state = State()
        state.move(Stone(1, 1))

        assertThat(state.isEmpty(Stone(1, 1))).isFalse
    }
}
