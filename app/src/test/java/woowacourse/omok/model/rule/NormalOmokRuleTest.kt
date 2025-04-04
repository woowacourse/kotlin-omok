package woowacourse.omok.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row

class NormalOmokRuleTest {
    @Test
    fun `대각선으로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(it)) to StoneColor.WHITE }

        val lastStone = pairToStone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `세로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneColor.WHITE }

        val lastStone = pairToStone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `가로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneColor.WHITE }

        val lastStone = pairToStone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val stonesList = List(6) { Position(Row(3), Col(it)) to StoneColor.WHITE }

        val lastStone = pairToStone(stonesList[stonesList.size - 3])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)

        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `6목이상이 아닌 정확한 오목임을 확인할 수 있다`() {
        val stonesList = List(6) { Position(Row(3), Col(it)) to StoneColor.WHITE }

        val lastStone = pairToStone(stonesList[stonesList.size - 3])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position, true)

        assertThat(whiteStoneResult).isFalse()
    }

    companion object {
        fun pairToStone(pair: Pair<Position, StoneColor>) = Stone(pair.first, pair.second)
    }
}
