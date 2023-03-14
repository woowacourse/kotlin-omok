package domain.state

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class WhiteWinTest {

    @Test
    fun `백돌 승리상태를 생성할 때 흑돌 수가 백돌 수보다 1개 더 많지 않으면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            WhiteWin(
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('C'), YCoordinate.of(3)),
                ),
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                ),
            )
        }.withMessage("백돌 승리 상태에서는 흑돌과 백돌의 개수가 같아야 합니다.")
    }
}
