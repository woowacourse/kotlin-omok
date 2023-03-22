package domain.rule

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class RefereeTest {

    @Test
    fun `33규칙을 넘겨줬을 때 두려는 돌이 33규칙을 만족하지 않으면 에러를 발생시킨다`() {
        val referee = Referee(listOf(ThreeThreeRule()))
        assertThatIllegalArgumentException().isThrownBy {
            referee.checkStone(
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
                ), BlackStone(Point.create('D', 12))
            )
        }.withMessage("흑돌은 33이면 안됩니다.")
    }

    @Test
    fun `33규칙 44규칙을 넘겨줬을 때 두려는 돌이 44규칙을 만족하지 않으면 에러를 발생시킨다`() {
        val referee = Referee(listOf(ThreeThreeRule(), FourFourRule()))
        assertThatIllegalArgumentException().isThrownBy {
            referee.checkStone(
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
        }.withMessage("흑돌은 44면 안됩니다.")
    }

}