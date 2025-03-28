package woowacourse.omok.domain.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.RuleNavigation

class OmokBoardTest {
    @Test
    fun `크기를 설정하지 않은 오목판은 15 x 15 사이즈이다`() {
        // given & when
        val actual = PlayingBoard(ruleNavigation = RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules)).board.value.size
        // then
        assertThat(actual).isEqualTo(15 * 15)
    }
}
