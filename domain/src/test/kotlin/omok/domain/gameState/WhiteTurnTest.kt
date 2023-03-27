package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `돌을 놓을 수 있다`() {
        val omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)
        val omokBoardState: GameState = WhiteTurn(omokBoard).play(point)
        assertThat(omokBoardState).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `게임 진행이 가능하다`() {
        assertThat(WhiteTurn(OmokBoard()).isRunning).isTrue
    }
}
