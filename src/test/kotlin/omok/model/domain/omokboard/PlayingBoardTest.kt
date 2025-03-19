package omok.model.domain.omokboard

import omok.POSITION_ONE_ONE
import omok.model.domain.omokboard.PointState.OCCUPIED_BLACK
import omok.model.domain.player.PlayerStone
import omok.model.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayingBoardTest {
    @Test
    fun `오목판 내에서 원하는 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val playingBoard = PlayingBoard()
        val board = playingBoard.board.value
        // when
        playingBoard.placeStone(PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE))
        val actual = board.values.first().state
        val expected = OCCUPIED_BLACK
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
