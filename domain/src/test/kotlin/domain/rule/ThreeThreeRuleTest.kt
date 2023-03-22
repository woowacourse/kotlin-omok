package domain.rule

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
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

        val actual = ThreeThreeRule().checkRule(
            Stones(
                setOf(
                    BlackStone(Point.create('C', 12)),
                    BlackStone(Point.create('E', 12)),
                    BlackStone(Point.create('D', 13)),
                    BlackStone(Point.create('D', 14)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10)),
                    WhiteStone(Point.create('F', 2))
                )
            ), BlackStone(
                Point.create('D', 12)
            )
        )
        assertThat(actual).isTrue
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

        val actual = ThreeThreeRule().checkRule(
            Stones(
                setOf(
                    BlackStone(Point.create('C', 12)),
                    BlackStone(Point.create('E', 12)),
                    BlackStone(Point.create('D', 13)),
                    BlackStone(Point.create('D', 14)),
                    WhiteStone(Point.create('I', 2)),
                    WhiteStone(Point.create('L', 4)),
                    WhiteStone(Point.create('I', 10)),
                    WhiteStone(Point.create('F', 12))
                )
            ), BlackStone(Point.create('D', 12))
        )
        assertFalse(actual)
    }
}