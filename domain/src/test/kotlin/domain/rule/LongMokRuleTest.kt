package domain.rule

import domain.stone.BlackStone
import domain.stone.Point
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

        val actual = LongMokRule().checkRule(
            Stones(
                setOf(
                    BlackStone(Point.create('E', 5)),
                    BlackStone(Point.create('F', 5)),
                    BlackStone(Point.create('G', 5)),
                    BlackStone(Point.create('H', 5)),
                    BlackStone(Point.create('J', 5)),
                    BlackStone(Point.create('K', 5)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10)),
                    WhiteStone(Point.create('F', 2)),
                    WhiteStone(Point.create('A', 2)),
                    WhiteStone(Point.create('B', 2))
                )
            ), BlackStone(Point.create('I', 5))
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

        val actual = LongMokRule().checkRule(
            Stones(
                setOf(
                    BlackStone(Point.create('H', 5)),
                    BlackStone(Point.create('J', 5)),
                    BlackStone(Point.create('K', 5)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10))
                )
            ), BlackStone(Point.create('I', 5))
        )
        assertFalse(actual)
    }
}