package omok.domain.gameState

import omok.OmokBoard
import omok.OmokPoint
import omok.gameState.WhiteWin
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class WhiteWinTest {
    @Test
    fun `게임이 끝난 상태에서 진행하면 에러 발생한다`() {
        val point = OmokPoint(1, 1)
        assertThatThrownBy {
            WhiteWin(OmokBoard()).play(point)
        }
            .message()
            .isEqualTo("승자가 나온 후엔 더이상 플레이 할 수 없습니다")
    }

    @Test
    fun `게임 진행이 불가능하다`() {
        assertThat(WhiteWin(OmokBoard()).isRunning).isFalse
    }
}
