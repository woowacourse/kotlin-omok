package omok.model.rule

import omok.model.stone.Stone
import omok.model.stone.StoneState
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NormalOmokRuleTest {
    @Test
    fun `대각선으로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(it)) to StoneState.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `세로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneState.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `가로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneState.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val stonesList = List(6) { Position(Row(3), Col(it)) to StoneState.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 3])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position)

        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `6목이상이 아닌 정확한 오목임을 확인할 수 있다`() {
        val stonesList = List(6) { Position(Row(3), Col(it)) to StoneState.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 3])

        val normalOmokRule = NormalOmokRule(15)
        val whiteStoneResult = normalOmokRule.isPositionOmok(stonesList.toMap(), lastStone.position, true)

        assertThat(whiteStoneResult).isFalse()
    }
}
