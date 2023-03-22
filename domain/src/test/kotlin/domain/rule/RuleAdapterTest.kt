package domain.rule

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class RuleAdapterTest {

    @Test
    fun `33규칙을 넘겨줬을 때 두려는 돌이 33규칙을 만족하지 않으면 에러를 발생시킨다`() {
        val ruleAdapter = RuleAdapter(listOf(ThreeThreeRule()))
        assertThatIllegalArgumentException().isThrownBy {
            ruleAdapter.checkStone(
                Stones(
                    setOf(
                        BlackStone('C', 12),
                        BlackStone('E', 12),
                        BlackStone('D', 13),
                        BlackStone('D', 14),
                        WhiteStone('I', 2),
                        WhiteStone('L', 4),
                        WhiteStone('I', 10),
                        WhiteStone('F', 2)
                    )
                ), BlackStone('D', 12)
            )
        }.withMessage("흑돌은 33이면 안됩니다.")
    }

    @Test
    fun `33규칙 44규칙을 넘겨줬을 때 두려는 돌이 44규칙을 만족하지 않으면 에러를 발생시킨다`() {
        val ruleAdapter = RuleAdapter(listOf(ThreeThreeRule(), FourFourRule()))
        assertThatIllegalArgumentException().isThrownBy {
            ruleAdapter.checkStone(
                Stones(
                    setOf(
                        BlackStone('E', 5),
                        BlackStone('F', 5),
                        BlackStone('G', 5),
                        BlackStone('H', 6),
                        BlackStone('H', 7),
                        BlackStone('H', 8),
                        WhiteStone('I', 2),
                        WhiteStone('L', 4),
                        WhiteStone('I', 10),
                        WhiteStone('F', 2),
                        WhiteStone('A', 2),
                        WhiteStone('B', 2)
                    )
                ), BlackStone('H', 5)
            )
        }.withMessage("흑돌은 44면 안됩니다.")
    }

}