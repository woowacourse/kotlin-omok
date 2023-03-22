package domain.state

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTurnTest {

    @Test
    fun `백돌을 둘 차례일 때 흑돌 수가 백돌 수보다 1개 더 많지 않으면 에러가 발생한다`() {

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 2)),
                BlackStone(PointAdapter.create('C', 3)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('J', 14))
            )
        )

        //then
        Assertions.assertThatIllegalArgumentException().isThrownBy {
            WhiteTurn(stones)
        }.withMessage("백돌 차례에서는 흑돌이 백돌보다 1개 더 많아야 합니다.")
    }

    @Test
    fun `둘 수 있는 곳에 백돌을 뒀을 때 게임이 안끝나면 흑돌을 둘 수 있는 상태를 반환한다`() {

        //given
        val stones = Stones(setOf(BlackStone(PointAdapter.create('B', 2))))
        val state: State = WhiteTurn(stones)
        val point = PointAdapter.create('A', 1)

        //when
        val actual = state.put(point)

        //then
        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
