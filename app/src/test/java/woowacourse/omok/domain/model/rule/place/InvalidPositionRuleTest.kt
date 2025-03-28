package woowacourse.omok.domain.model.rule.place

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_ZERO_ZERO
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val omokGame: OmokGame = OmokGame()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ZERO_ZERO)

        // when
        val actual = InvalidPositionRule().perform(omokGame.board, playerStone1)
        val expected = PlaceResult.Failure.InvalidPosition

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
