package domain.rule

import domain.BlackStone
import domain.Stones
import domain.WhiteStone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FourFourRuleTest {


    @Test
    fun `흑돌을 뒀을 때 44면 true를 반환한다`() {

//        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         8 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         7 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──●──●──●──●──┼──┼──┼──┼──┼──┼──┤
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
        assertTrue(actual)
    }

    @Test
    fun `흑돌을 뒀을 때 44가 아니면 false를 반환한다`() {

//         9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         7 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──●──●──●──●──┼──┼──┼──┼──┼──┼──┤
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
                    BlackStone('H', 6),
                    BlackStone('H', 7),
                    WhiteStone('I', 2),
                    WhiteStone('L', 4),
                    WhiteStone('I', 10),
                    WhiteStone('F', 2),
                    WhiteStone('A', 2)
                )
            ), BlackStone('H', 5)
        )
        assertFalse(actual)
    }
}