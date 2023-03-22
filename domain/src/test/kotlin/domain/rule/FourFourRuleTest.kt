package domain.rule

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
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
                    BlackStone(Point.create('E', 5)),
                    BlackStone(Point.create('F', 5)),
                    BlackStone(Point.create('G', 5)),
                    BlackStone(Point.create('H', 6)),
                    BlackStone(Point.create('H', 7)),
                    BlackStone(Point.create('H', 8)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10)),
                    WhiteStone(Point.create('F', 2)),
                    WhiteStone(Point.create('A', 2)),
                    WhiteStone(Point.create('B', 2))
                )
            ), BlackStone(Point.create('H', 5))
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
                    BlackStone(Point.create('E', 5)),
                    BlackStone(Point.create('F', 5)),
                    BlackStone(Point.create('G', 5)),
                    BlackStone(Point.create('H', 6)),
                    BlackStone(Point.create('H', 7)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10)),
                    WhiteStone(Point.create('F', 2)),
                    WhiteStone(Point.create('A', 2))
                )
            ), BlackStone(Point.create('H', 5))
        )
        assertFalse(actual)
    }
}