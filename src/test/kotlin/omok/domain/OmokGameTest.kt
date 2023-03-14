package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑돌을 놓을 수 있다`() {
        val omokGame = OmokGame()
        val omokBoard = OmokBoard(YLine())
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))
        val omokBoardState = omokGame.playBlack(omokBoard, point).yLine[point.y][point.x]
        assertThat(omokBoardState).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `백돌을 놓을 수 있다`() {
        val omokGame = OmokGame()
        val omokBoard = OmokBoard(YLine())
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        val omokBoardState = omokGame.playWhite(omokBoard, point).yLine[point.y][point.x]
        assertThat(omokBoardState).isEqualTo(StoneState.WHITE)
    }
}
