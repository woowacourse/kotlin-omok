package omok.domain.rule

import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.placeresult.InvalidMove
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard(rules = OmokRule.rules)
        val playerStone1: PlayerStone =
            PlayerStone(StoneColor.BLACK, Position(0, 'X'))

        // when
        val actual = InvalidPositionRule().place(playingBoard.board, playerStone1)
        val expected = InvalidMove.InvalidPosition

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard(rules = OmokRule.rules)
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'A'))

        // when
        playingBoard.placeStone(playerStone1)
        val actual = InvalidPositionRule().place(playingBoard.board, playerStone1)
        val expected = InvalidMove.AlreadyExistStone

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
