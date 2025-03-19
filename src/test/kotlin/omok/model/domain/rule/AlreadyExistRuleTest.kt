package omok.model.domain.rule

import omok.POSITION_ONE_ONE
import omok.model.domain.omokboard.PlayingBoard
import omok.model.domain.player.PlayerStone
import omok.model.domain.player.StoneColor
import omok.model.domain.rule.AlreadyExistRule
import omok.model.domain.rule.PlaceResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AlreadyExistRuleTest {
    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)

        playingBoard.placeStone(playerStone1)
        val actual = AlreadyExistRule().canPlace(playingBoard.board, playerStone1)
        val expected = PlaceResult.Failure.AlreadyExist

        assertThat(actual).isEqualTo(expected)
    }
}
