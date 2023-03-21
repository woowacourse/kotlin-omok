package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BlackWinTest {
    @Test
    fun `게임이 끝난 상태에서 진행하면 에러 발생한다`() {
        val point = OmokPoint(1, 1)
        val exception = assertThrows<IllegalStateException> { BlackWin(OmokBoard()).play(point) }
        assertThat(exception.message).isEqualTo("승자가 나온 후엔 더이상 플레이 할 수 없습니다")
    }

    @Test
    fun `게임 진행이 불가능하다`() {
        assertThat(BlackWin(OmokBoard()).isRunning).isFalse
    }
}
