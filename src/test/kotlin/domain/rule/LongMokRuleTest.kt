package domain.rule

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LongMokRuleTest {
    @Test
    fun `흑돌을 뒀을 때 장목이면 true를 반환한다`() {

//         9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──●──●──●──●──┼──●──●──┼──┼──┼──┤
//         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        val actual = FourFourRule().checkRule(
            Stones(
                setOf(
                    BlackStone('E', 5),
                    BlackStone('F', 5),
                    BlackStone('G', 5),
                    BlackStone('H', 5),
                    BlackStone('J', 5),
                    BlackStone('K', 5),
                    WhiteStone('I', 2),
                    WhiteStone('L', 4),
                    WhiteStone('I', 10),
                    WhiteStone('F', 2),
                    WhiteStone('A', 2),
                    WhiteStone('B', 2)
                )
            ), BlackStone('I', 5)
        )
        assertTrue(actual)
    }

    @Test
    fun `흑돌을 뒀을 때 장목이 아니면 false를 반환한다`() {

//         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──┼──┼──┼──●──●──●──●──┼──┼──┼──┤
//         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        val actual = FourFourRule().checkRule(
            Stones(
                setOf(
                    BlackStone('H', 5),
                    BlackStone('J', 5),
                    BlackStone('K', 5),
                    WhiteStone('I', 2),
                    WhiteStone('L', 4),
                    WhiteStone('I', 10)
                )
            ), BlackStone('I', 5)
        )
        assertFalse(actual)
    }
}