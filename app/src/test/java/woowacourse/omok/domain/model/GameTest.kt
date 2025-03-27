package woowacourse.omok.domain.model

import omok.doubleFourFixture
import omok.doubleFourStone
import omok.doubleThreeFixture
import omok.doubleThreeStone
import omok.horizontalFourStones
import omok.longMoveStone
import omok.stoneLongMoveFixture
import omok.stoneOneAndOne
import omok.stoneSixAndSix
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class GameTest {
    private lateinit var game: Game
    private val omokRule = OmokRule(RenjuRuleAdapter(BlackRenjuRule()))

    fun setUp() {
        game = Game(omokRule, Stones(listOf()), Turn(StoneType.BLACK))
    }

    @Test
    fun `33 렌주룰로 둘 수 없는 경우`() {
        game = Game(omokRule, doubleThreeFixture)
        assertThat(game.canPlace(doubleThreeStone.position)).isInstanceOf(RuleResult.RenJuRule::class.java)
    }

    @Test
    fun `44 렌주룰로 둘 수 없는 경우`() {
        game = Game(omokRule, doubleFourFixture)
        assertThat(game.canPlace(doubleFourStone.position)).isInstanceOf(RuleResult.RenJuRule::class.java)
    }

    @Test
    fun `장목 렌주룰로 둘 수 없는 경우`() {
        game = Game(omokRule, stoneLongMoveFixture)
        assertThat(game.canPlace(longMoveStone.position)).isInstanceOf(RuleResult.RenJuRule::class.java)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        game = Game(omokRule, horizontalFourStones)
        assertThat(game.canPlace(stoneOneAndOne.position)).isInstanceOf(RuleResult.DuplicatePosition::class.java)
    }

    @Test
    fun `바둑돌을 둘 위치가 빈 경우 바둑돌을 둘 수 있다`() {
        game = Game(omokRule, horizontalFourStones)
        assertThat(game.canPlace(stoneSixAndSix.position)).isInstanceOf(RuleResult.OnRule::class.java)
    }
}
