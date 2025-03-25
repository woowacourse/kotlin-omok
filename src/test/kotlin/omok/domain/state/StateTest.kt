package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.rule.BlackStoneRule
import omok.domain.rule.WhiteStoneRule
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones
import omok.fixture.A1
import omok.fixture.A2
import omok.fixture.A3
import omok.fixture.A4
import omok.fixture.A5
import omok.fixture.B1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTest {
    private val blackStoneRule = BlackStoneRule()
    private val whiteStoneRule = WhiteStoneRule()

    @Test
    fun `흑의 차례가 끝나면 백의 차례이다`() {
        val omokBoard = OmokBoard()
        val state = BlackTurn(omokBoard)
        val nextState = state.place(A1)
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백의 차례가 끝나면 흑의 차례이다`() {
        val omokBoard = OmokBoard()
        val state = WhiteTurn(omokBoard)
        val nextState = state.place(A1)
        assertThat(nextState).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = Stones(blackStoneRule, setOf(A1, A2, A3, A4))
        val whiteStones = Stones(whiteStoneRule, emptySet())
        val omokBoard = OmokBoard(blackStones = blackStones, whiteStones = whiteStones)
        val state = BlackTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val blackStones = Stones(blackStoneRule, emptySet())
        val whiteStones = Stones(whiteStoneRule, setOf(A1, A2, A3, A4))
        val omokBoard = OmokBoard(blackStones = blackStones, whiteStones = whiteStones)
        val state = WhiteTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `흑돌을 놓은 다음에는 백돌을 놓는다`() {
        val blackStones = Stones(blackStoneRule, emptySet())
        val whiteStones = Stones(whiteStoneRule, emptySet())
        val omokBoard = OmokBoard(blackStones = blackStones, whiteStones = whiteStones)
        val state = WhiteTurn(omokBoard)
        val expected = StoneColor.WHITE
        assertThat(state.stoneColor).isEqualTo(expected)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 흑이 승리한다`() {
        val blackStones = Stones(blackStoneRule, setOf(A1, A2, A3, A4))
        val whiteStones = Stones(whiteStoneRule, setOf(B1))
        val omokBoard = OmokBoard(blackStones = blackStones, whiteStones = whiteStones)
        val state = BlackTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
        assertThat((nextState as Finished).winnerColor).isEqualTo(StoneColor.BLACK)
    }
}
