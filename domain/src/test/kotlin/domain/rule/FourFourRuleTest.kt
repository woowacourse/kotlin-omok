package domain.rule

import PointAdapter
import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('E', 5)),
                BlackStone(PointAdapter.create('F', 5)),
                BlackStone(PointAdapter.create('G', 5)),
                BlackStone(PointAdapter.create('H', 6)),
                BlackStone(PointAdapter.create('H', 7)),
                BlackStone(PointAdapter.create('H', 8)),
                WhiteStone(PointAdapter.create('I', 2)),
                WhiteStone(PointAdapter.create('L', 4)),
                WhiteStone(PointAdapter.create('I', 10)),
                WhiteStone(PointAdapter.create('F', 2)),
                WhiteStone(PointAdapter.create('A', 2)),
                WhiteStone(PointAdapter.create('B', 2))
            )
        )
        val justPlacedStone = BlackStone(PointAdapter.create('H', 5))

        //when
        val actual = FourFourRule().checkRule(stones, justPlacedStone)

        //then
        assertThat(actual).isTrue
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('E', 5)),
                BlackStone(PointAdapter.create('F', 5)),
                BlackStone(PointAdapter.create('G', 5)),
                BlackStone(PointAdapter.create('H', 6)),
                BlackStone(PointAdapter.create('H', 7)),
                WhiteStone(PointAdapter.create('I', 2)),
                WhiteStone(PointAdapter.create('L', 4)),
                WhiteStone(PointAdapter.create('I', 10)),
                WhiteStone(PointAdapter.create('F', 2)),
                WhiteStone(PointAdapter.create('A', 2))
            )
        )
        val justPlacedStone = BlackStone(PointAdapter.create('H', 5))

        //when
        val actual = FourFourRule().checkRule(stones, justPlacedStone)

        //then
        assertThat(actual).isFalse
    }
}