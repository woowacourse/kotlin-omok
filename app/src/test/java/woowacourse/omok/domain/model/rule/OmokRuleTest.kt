package woowacourse.omok.domain.model.rule

import io.kotest.matchers.shouldBe
import omok.diagonalDownFourStones
import omok.diagonalDownWinStone
import omok.doubleFourFixture
import omok.doubleFourStone
import omok.doubleThreeFixture
import omok.doubleThreeStone
import omok.horizontalFourStones
import omok.horizontalWinStone
import omok.longMoveStone
import omok.stoneLongMoveFixture
import omok.stoneOneAndOne
import omok.stoneSixAndSix
import omok.verticalFourStones
import omok.verticalWinStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult

class OmokRuleTest {
    private val omokRule: OmokRule = OmokRule(RenjuRuleAdapter(BlackRenjuRule()))

    @Test
    fun `흑돌 차례일 때 장목이면 둘 수 없다`() {
        omokRule.canPlace(
            stoneLongMoveFixture,
            longMoveStone,
        ) shouldBe RuleResult.RenJuRule
    }

    @Test
    fun `흑돌 차례일 때 3-3이면 둘 수 없다`() {
        assertThat(
            omokRule.canPlace(
                doubleThreeFixture,
                doubleThreeStone,
            ),
        ).isInstanceOf(RuleResult.RenJuRule::class.java)
    }

    @Test
    fun `흑돌 차례일 때 4-4면 둘 수 없다`() {
        assertThat(
            omokRule.canPlace(
                doubleFourFixture,
                doubleFourStone,
            ),
        ).isInstanceOf(RuleResult.RenJuRule::class.java)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        assertThat(
            omokRule.canPlace(
                horizontalFourStones,
                stoneOneAndOne,
            ),
        ).isInstanceOf(RuleResult.DuplicatePosition::class.java)
    }

    @Test
    fun `세로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        omokRule.checkWin(verticalFourStones, verticalWinStone) shouldBe true
    }

    @Test
    fun `대각선 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        omokRule.checkWin(diagonalDownFourStones, diagonalDownWinStone) shouldBe true
    }

    @Test
    fun `가로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        omokRule.checkWin(horizontalFourStones, horizontalWinStone) shouldBe true
    }

    @Test
    fun `바둑돌을 둘 위치가 빈 경우 바둑돌을 둘 수 있다`() {
        assertThat(
            omokRule.canPlace(
                horizontalFourStones,
                stoneSixAndSix,
            ),
        ).isInstanceOf(RuleResult.OnRule::class.java)
    }
}
