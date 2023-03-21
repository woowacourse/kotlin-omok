package domain.rule

import domain.BlackStone
import domain.Stone
import domain.Stones
import domain.WhiteStone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ThreeThreeRuleTest {
    @Test
    fun `흑돌을 뒀을 때 33이면 true를 반환한다`() {
//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//        14 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        13 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        12 ├──┼──●──●──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//       ... ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        val actual = FourFourRule().checkRule(
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
        assertTrue(actual)
    }

    @Test
    fun `흑돌을 뒀을 때 33이 아니면 false를 반환한다`() {
//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//        14 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        13 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        12 ├──┼──●──●──●──○──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//       ... ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        val actual = FourFourRule().checkRule(
            Stones(
                setOf(
                    BlackStone('C', 12),
                    BlackStone('E', 12),
                    BlackStone('D', 13),
                    BlackStone('D', 14),
                    WhiteStone('I', 2),
                    WhiteStone('L', 4),
                    WhiteStone('I', 10),
                    WhiteStone('F', 12)
                )
            ), BlackStone('D', 12)
        )
        assertFalse(actual)
    }
}