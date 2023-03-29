package domain.state

import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Stones
import domain.domain.state.BlackTurn
import domain.domain.state.WhiteTurn
import domain.domain.state.WhiteWin
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class WhiteTurnTest {
    @Test
    fun `white to white - 자리가 비어있지 않은 경우 같은 상태를 반환한다`() {
        val whiteTurn = WhiteTurn(Stones().apply { addStone(Position(1, 1), WHITE) })

        val actual = whiteTurn.toNextState(Position(1, 1))

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `white to black - 승리가 아니고 문제없이 돌을 놓을 경우 반대 턴으로 넘어간다`() {
        val whiteTurn = WhiteTurn(Stones())

        val actual = whiteTurn.toNextState(Position(1, 1))

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `white to whiteWin - 화이트턴에서 오목을 만들면 화이트윈으로 넘어간다`() {
        val whiteTurn = WhiteTurn(BlackTurnTest.getWinStones(WHITE))

        val actual = whiteTurn.toNextState(BlackTurnTest.getWinPosition())

        assertThat(actual).isInstanceOf(WhiteWin::class.java)
    }

    @Test
    fun `white to whiteWin - 화이트턴에서 장목을 만들면 화이트윈으로 넘어간다`() {
        val whiteTurn = WhiteTurn(BlackTurnTest.getOverLinedStones(WHITE))

        val actual = whiteTurn.toNextState(BlackTurnTest.getOverLinedPosition())

        assertThat(actual).isInstanceOf(WhiteWin::class.java)
    }
}
