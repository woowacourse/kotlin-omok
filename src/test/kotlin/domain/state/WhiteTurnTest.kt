package domain.state

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
import domain.stone.XCoordinate
import domain.stone.YCoordinate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WhiteTurnTest {

    @Test
    fun `백돌을 둘 차례일 때 흑돌 수가 백돌 수보다 1개 더 많지 않으면 에러가 발생한다`() {
        Assertions.assertThatIllegalArgumentException().isThrownBy {
            WhiteTurn(
                Stones(setOf(BlackStone('B', 2), BlackStone('C', 3), WhiteStone('B', 12), WhiteStone('J', 14)))
            )
        }.withMessage("백돌 차례에서는 흑돌이 백돌보다 1개 더 많아야 합니다.")
    }

    @Test
    fun `둘 수 있는 곳에 백돌을 뒀을 때 게임이 안끝나면 흑돌을 둘 수 있는 상태를 반환한다`() {
        val state: State = WhiteTurn(Stones(setOf(BlackStone(XCoordinate.of('B'), YCoordinate.of(2)))))
        val actual = state.put(Point(XCoordinate.of('A'), YCoordinate.of(1)))

        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
