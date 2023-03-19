package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WhiteWinTest {
    @Test
    fun `게임이 끝난 상태에서 진행하면 에러 발생한다`() {
        val point = OmokPoint('A', 1)
        assertThrows<IllegalStateException> { WhiteWin(OmokBoard()).play(point) }
    }

    @Test
    fun `게임 진행이 불가능하다`() {
        Assertions.assertThat(WhiteWin(OmokBoard()).isRunning).isFalse
    }
}
