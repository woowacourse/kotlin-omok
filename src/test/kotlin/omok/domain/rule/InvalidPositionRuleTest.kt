package omok.domain.rule

import omok.POSITION_ZERO_ZERO
import omok.domain.omokboard.PlayingBoard
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone =
            PlayerStone(StoneColor.BLACK, POSITION_ZERO_ZERO)

        // when
        val actual = InvalidPositionRule().canPlace(playingBoard.board, playerStone1)
        val expected = PlaceResult.Failure.InvalidPosition

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
