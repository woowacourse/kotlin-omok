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

    @Test
    fun `두 state를 합하면 두 state 모두 돌이 없는 경우를 제외하고 모두 돌이 있다`() {
        // given
        val state1 = State(
            listOf(
                listOf(true, false, true),
                listOf(false, false, true),
                listOf(true, true, false)
            )
        )

        val state2 = State(
            listOf(
                listOf(false, true, false),
                listOf(false, false, false),
                listOf(false, false, true)
            )
        )

        val expect = State(
            listOf(
                listOf(true, true, true),
                listOf(false, false, true),
                listOf(true, true, true)
            )
        )

        // when
        val actual = state1 + state2

        // then
        assertThat(actual).isEqualTo(expect)
    }
}
