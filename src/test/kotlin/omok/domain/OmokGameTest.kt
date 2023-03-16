package omok.domain

import omok.domain.state.EmptyStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `돌을 놓을 수 있다`() {
        val omokGame = OmokGame()
        val omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)
        val omokBoardState = omokGame.playNext(omokBoard, point)[point.y][point.x]
        assertThat(omokBoardState).isNotEqualTo(EmptyStoneState)
    }
}
