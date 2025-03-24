package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.rule.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayingBoardTest {
    @Test
    fun `오목판 내에서 원하는 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val playingBoard = PlayingBoard(rules = OmokRule.rules)

        // when
        playingBoard.placeStone(PlayerStone(StoneColor.BLACK, Position(1, 'A')))

        val actual = playingBoard.board.value.values.first()
        val expected = PointState.OCCUPIED(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
