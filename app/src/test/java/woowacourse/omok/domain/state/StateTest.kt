package woowacourse.omok.domain.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.fixture.A1
import woowacourse.omok.fixture.A2
import woowacourse.omok.fixture.A3
import woowacourse.omok.fixture.A4
import woowacourse.omok.fixture.A5

class StateTest {
    @Test
    fun `흑의 차례가 끝나면 백의 차례이다`() {
        val omokBoard = OmokBoard()
        val state = BlackTurn(omokBoard)
        val nextState = (state.place(A1.toBlackStone()) as PlaceResult.Placed).state
        assertThat(nextState).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백의 차례가 끝나면 흑의 차례이다`() {
        val omokBoard = OmokBoard()
        val state = WhiteTurn(omokBoard)
        val nextState = (state.place(A1.toWhiteStone()) as PlaceResult.Placed).state
        assertThat(nextState).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val stones = OmokStones(setOf(A1, A2, A3, A4).toBlackStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = BlackTurn(omokBoard)
        val nextState = (state.place(A5.toBlackStone()) as PlaceResult.Placed).state
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임을 종료한다`() {
        val stones = OmokStones(setOf(A1, A2, A3, A4).toWhiteStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = WhiteTurn(omokBoard)
        val nextState = (state.place(A5.toWhiteStone()) as PlaceResult.Placed).state
        assertThat(nextState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 흑이 승리한다`() {
        val stones = OmokStones(setOf(A1, A2, A3, A4).toBlackStones())
        val omokBoard = OmokBoard(stones = stones)
        val state = BlackTurn(omokBoard)
        val nextState = (state.place(A5.toBlackStone()) as PlaceResult.Placed).state
        assertThat((nextState as Finished).winnerColor).isEqualTo(StoneColor.BLACK)
    }

    private fun Set<Point>.toBlackStones(): Set<Stone> = this.map { Stone(StoneColor.BLACK, it) }.toSet()

    private fun Set<Point>.toWhiteStones(): Set<Stone> = this.map { Stone(StoneColor.WHITE, it) }.toSet()

    private fun Point.toBlackStone(): Stone = Stone(StoneColor.BLACK, this)

    private fun Point.toWhiteStone(): Stone = Stone(StoneColor.WHITE, this)
}
