package woowacourse.omok.domain.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard(whiteRules = OmokRules.whiteRules, blackRules = OmokRules.blackRules)
        val playerStone1: PlayerStone =
            PlayerStone(StoneColor.BLACK, Position(0 to 20))

        // when
        val actual = InvalidPositionRule().place(playingBoard.board, playerStone1)
        val expected = InvalidMove.InvalidPosition

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard(whiteRules = OmokRules.whiteRules, blackRules = OmokRules.blackRules)
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1 to 1))

        // when
        playingBoard.placeStone(playerStone1)
        val actual = InvalidPositionRule().place(playingBoard.board, playerStone1)
        val expected = InvalidMove.AlreadyExistStone

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
