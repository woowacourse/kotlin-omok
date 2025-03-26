package woowacourse.omok.domain.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_ONE_ONE

class PlayingBoardTest {
    @Test
    fun `오목판 내에서 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val playingBoard = PlayingBoard()
        val board = playingBoard.board.value

        // when
        playingBoard.placeStone(emptyList(), POSITION_ONE_ONE)
        val actual = board.values.first().state
        val expected = IntersectionState.OCCUPIED_BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
