package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones
import omok.fixture.A1
import omok.fixture.A2
import omok.fixture.A3
import omok.fixture.A4
import omok.fixture.A5
import omok.fixture.B1
import omok.fixture.B2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTest {
    @Test
    fun `흑부터 돌을 놓는다`() {
        val state = Ready()
        val nextState = state.place(A1)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `흑의 차례가 끝나면 백의 차례이다`() {
        val state = BlackTurn(BlackStones(), WhiteStones())
        val nextState = state.place(A1)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백의 차례가 끝나면 흑의 차례이다`() {
        val state = WhiteTurn(BlackStones(), WhiteStones())
        val nextState = state.place(A1)
        assertThat(nextState).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = BlackStones(setOf(A1, A2, A3, A4))
        val whiteStones = WhiteStones(emptySet())
        val state = BlackTurn(blackStones, whiteStones)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = BlackStones(emptySet())
        val whiteStones = WhiteStones(setOf(A1, A2, A3, A4))
        val state = WhiteTurn(blackStones, whiteStones)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val blackStones = BlackStones(setOf(A1, A2))
        val whiteStones = WhiteStones(setOf(A3, A4))
        val state = BlackTurn(blackStones, whiteStones)
        val expected = A4
        assertThat(state.lastStonePoint()).isEqualTo(expected)
    }

    @Test
    fun `흑돌을 놓은 다음에는 백돌을 놓는다`() {
        val blackStones = BlackStones(emptySet())
        val whiteStones = WhiteStones(emptySet())
        val state = WhiteTurn(blackStones, whiteStones)
        val expected = StoneColor.WHITE
        assertThat(state.nextStoneColor()).isEqualTo(expected)
    }

    @Test
    fun `더 이상 돌을 놓을 수 없고 승자가 없으면 무승부이다`() {
        val blackStones = BlackStones(setOf(A1, A2))
        val whiteStones = WhiteStones(setOf(B1))
        val state = WhiteTurn(blackStones, whiteStones)
        val nextState = state.place(B2, 2)
        assertThat(nextState).isInstanceOf(Draw::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 흑이 승리한다`() {
        val blackStones = BlackStones(setOf(A1, A2, A3, A4))
        val whiteStones = WhiteStones(setOf(B1))
        val state = BlackTurn(blackStones, whiteStones)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(BlackWin::class.java)
    }
}
