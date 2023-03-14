package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑돌을 놓을 수 있다`() {
        val omokGame = OmokGame()
        val omokBoard = OmokBoard(YLine())
        val xCoordinate = XCoordinate('A')
        val yCoordinate = YCoordinate(1)
        val omokBoardState = omokGame.playBlack(omokBoard, xCoordinate, yCoordinate).yLine[yCoordinate][xCoordinate]
        assertThat(omokBoardState).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `백돌을 놓을 수 있다`() {
        val omokGame = OmokGame()
        val omokBoard = OmokBoard(YLine())
        val xCoordinate = XCoordinate('A')
        val yCoordinate = YCoordinate(1)
        val omokBoardState = omokGame.playWhite(omokBoard, xCoordinate, yCoordinate).yLine[yCoordinate][xCoordinate]
        assertThat(omokBoardState).isEqualTo(StoneState.WHITE)
    }
}
