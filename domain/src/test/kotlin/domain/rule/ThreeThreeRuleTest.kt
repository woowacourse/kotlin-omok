package domain.rule

import domain.stone.BlackStone
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
                    BlackStone(PointAdapter.create('C', 12)),
                    BlackStone(PointAdapter.create('E', 12)),
                    BlackStone(PointAdapter.create('D', 13)),
                    BlackStone(PointAdapter.create('D', 14)),
                    WhiteStone(PointAdapter.create('I', 2)),
                    WhiteStone(PointAdapter.create('L', 4)),
                    WhiteStone(PointAdapter.create('I', 10)),
                    WhiteStone(PointAdapter.create('F', 2))
                )
            ), BlackStone(
                PointAdapter.create('D', 12)
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
                    BlackStone(PointAdapter.create('C', 12)),
                    BlackStone(PointAdapter.create('E', 12)),
                    BlackStone(PointAdapter.create('D', 13)),
                    BlackStone(PointAdapter.create('D', 14)),
                    WhiteStone(PointAdapter.create('I', 2)),
                    WhiteStone(PointAdapter.create('L', 4)),
                    WhiteStone(PointAdapter.create('I', 10)),
                    WhiteStone(PointAdapter.create('F', 12))
                )
            ), BlackStone(PointAdapter.create('D', 12))
        )
        assertFalse(actual)
    }
}