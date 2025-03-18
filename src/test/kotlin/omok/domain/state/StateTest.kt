package omok.domain.state

import omok.domain.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTest {
    @Test
    fun `흑부터 돌을 놓는다`() {
        val state = Ready()
        val point = Point(0, 0)
        val nextState = state.place(point)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `흑의 차례가 끝나면 백의 차례이다`() {
        val state = BlackTurn(emptyList())
        val point = Point(0, 0)
        val nextState = state.place(point)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백의 차례가 끝나면 흑의 차례이다`() {
        val state = WhiteTurn(emptyList())
        val point = Point(0, 0)
        val nextState = state.place(point)
        assertThat(nextState).isInstanceOf(BlackTurn::class.java)
    }
}
