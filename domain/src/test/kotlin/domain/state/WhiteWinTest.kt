package domain.state

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class WhiteWinTest {

    @Test
    fun `백돌 승리상태를 생성할 때 흑돌 수가 백돌 수보다 1개 더 많지 않으면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            WhiteWin(
                Stones(setOf(BlackStone('B', 2), BlackStone('C', 3), WhiteStone('B', 12)))
            )
        }.withMessage("백돌 승리 상태에서는 흑돌과 백돌의 개수가 같아야 합니다.")
    }
}
