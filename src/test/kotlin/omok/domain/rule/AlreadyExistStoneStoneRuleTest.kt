package omok.domain.rule

import omok.POSITION_ONE_ONE
import omok.domain.omokboard.PlayingBoard
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AlreadyExistStoneStoneRuleTest {
    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard(rules = OmokRule.rules)
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)

        // when
        playingBoard.placeStone(playerStone1)
        val actual = AlreadyExistStoneRule().canPlace(playingBoard.board, playerStone1)
        val expected = PlaceResult.Failure.AlreadyExistStone

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
