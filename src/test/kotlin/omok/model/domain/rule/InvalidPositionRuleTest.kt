package omok.model.domain.rule

import omok.POSITION_ZERO_ZERO
import omok.model.domain.omokboard.PlayingBoard
import omok.model.domain.player.PlayerStone
import omok.model.domain.player.StoneColor
import omok.model.domain.rule.InvalidPositionRule
import omok.model.domain.rule.PlaceResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone =
            PlayerStone(StoneColor.BLACK, POSITION_ZERO_ZERO)

        val actual = InvalidPositionRule().canPlace(playingBoard.board, playerStone1)
        val expected = PlaceResult.Failure.InvalidPosition

        assertThat(actual).isEqualTo(expected)
    }
}
