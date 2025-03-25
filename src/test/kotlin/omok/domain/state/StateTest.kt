package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.rule.OmokRule
import omok.domain.stone.OmokStones
import omok.domain.stone.Stone
import omok.domain.stone.StoneColor
import omok.fixture.A1
import omok.fixture.A2
import omok.fixture.A3
import omok.fixture.A4
import omok.fixture.A5
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTest {
    private val omokRule = OmokRule()

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
        val stones = OmokStones(omokRule, setOf(A1, A2, A3, A4).toBlackStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = BlackTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val stones = OmokStones(omokRule, setOf(A1, A2, A3, A4).toWhiteStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = WhiteTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 흑이 승리한다`() {
        val stones = OmokStones(omokRule, setOf(A1, A2, A3, A4).toBlackStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = BlackTurn(omokBoard)
        val nextState = state.place(A5)
        assertThat(nextState).isInstanceOf(Finished::class.java)
        assertThat((nextState as Finished).winnerColor).isEqualTo(StoneColor.BLACK)
    }

    private fun Set<Point>.toBlackStones(): Set<Stone> = this.map { Stone(StoneColor.BLACK, it) }.toSet()

    private fun Set<Point>.toWhiteStones(): Set<Stone> = this.map { Stone(StoneColor.WHITE, it) }.toSet()
}
