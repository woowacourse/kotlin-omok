package woowacourse.omok.domain.model.rule.place

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_ONE_ONE
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor

class AlreadyExistStoneStoneRuleTest {
    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val omokGame: OmokGame = OmokGame()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        val actual = AlreadyExistStoneRule().perform(omokGame.game.board, playerStone1)
        val expected = PlaceResult.Failure.AlreadyExistStone

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
