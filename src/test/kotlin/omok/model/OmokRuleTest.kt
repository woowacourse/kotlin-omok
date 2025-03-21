package omok.model

import omok.model.rule.OmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {
    @Test
    fun `대각선으로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(it)) to StoneColor.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val omokRule = OmokRule(15)
        val whiteStoneResult = omokRule.isLastStoneOmok(stonesList.toMap(), lastStone)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `세로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneColor.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val omokRule = OmokRule(15)
        val whiteStoneResult = omokRule.isLastStoneOmok(stonesList.toMap(), lastStone)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `가로로 오목임을 확인할 수 있다`() {
        val stonesList = List(5) { Position(Row(it), Col(1)) to StoneColor.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 1])

        val omokRule = OmokRule(15)
        val whiteStoneResult = omokRule.isLastStoneOmok(stonesList.toMap(), lastStone)
        assertThat(whiteStoneResult).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val stonesList = List(6) { Position(Row(3), Col(it)) to StoneColor.WHITE }

        val lastStone = Stone(stonesList[stonesList.size - 3])

        val omokRule = OmokRule(15)
        val whiteStoneResult = omokRule.isLastStoneOmok(stonesList.toMap(), lastStone)

        assertThat(whiteStoneResult).isTrue()
    }
}
