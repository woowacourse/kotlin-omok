package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones
import omok.fixture.A1
import omok.fixture.A2
import omok.fixture.A3
import omok.fixture.A4
import omok.fixture.A5
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
        val state = BlackTurn(Stones(color = StoneColor.BLACK), Stones(color = StoneColor.WHITE))
        val point = Point(0, 0)
        val nextState = state.place(point)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백의 차례가 끝나면 흑의 차례이다`() {
        val state = WhiteTurn(Stones(color = StoneColor.BLACK), Stones(color = StoneColor.WHITE))
        val point = Point(0, 0)
        val nextState = state.place(point)
        assertThat(nextState).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = Stones(setOf(A1, A2, A3, A4), StoneColor.BLACK)
        val whiteStones = Stones(emptySet(), StoneColor.WHITE)
        val state = BlackTurn(blackStones, whiteStones)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = Stones(emptySet(), StoneColor.BLACK)
        val whiteStones = Stones(setOf(A1, A2, A3, A4), StoneColor.WHITE)
        val state = WhiteTurn(blackStones, whiteStones)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val blackStones = Stones(setOf(A1, A2), StoneColor.BLACK)
        val whiteStones = Stones(setOf(A3, A4), StoneColor.WHITE)
        val state = BlackTurn(blackStones, whiteStones)
        val expected = A4
        assertThat(state.lastStonePoint()).isEqualTo(expected)
    }

    @Test
    fun `흑돌을 놓은 다음에는 백돌을 놓는다`() {
        val blackStones = Stones(emptySet(), StoneColor.BLACK)
        val whiteStones = Stones(emptySet(), StoneColor.WHITE)
        val state = WhiteTurn(blackStones, whiteStones)
        val expected = StoneColor.WHITE
        assertThat(state.nextStoneColor()).isEqualTo(expected)
    }
}
