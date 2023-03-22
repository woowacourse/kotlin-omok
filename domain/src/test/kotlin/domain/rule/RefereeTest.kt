package domain.rule

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class RefereeTest {

    @Test
    fun `33규칙을 넘겨줬을 때 두려는 돌이 33규칙을 만족하지 않으면 에러를 발생시킨다`() {

        //given
        val referee = Referee(listOf(ThreeThreeRule()))
        val stones = Stones(
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
        )
        val justPlacedStone = BlackStone(PointAdapter.create('D', 12))

        //then
        assertThatIllegalArgumentException().isThrownBy {
            referee.checkStone(stones, justPlacedStone)
        }.withMessage("흑돌은 33이면 안됩니다.")
    }

    @Test
    fun `33규칙 44규칙을 넘겨줬을 때 두려는 돌이 44규칙을 만족하지 않으면 에러를 발생시킨다`() {

        //given
        val referee = Referee(listOf(ThreeThreeRule(), FourFourRule()))
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

        //then
        assertThatIllegalArgumentException().isThrownBy {
            referee.checkStone(stones, justPlacedStone)
        }.withMessage("흑돌은 44면 안됩니다.")
    }

}