package domain.state

import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BlackWinTest {

    @Test
    fun `흑돌이 이긴 상태에서 바둑을 두려고 하면 에러가 발생한다`() {
        //given
        val stones = Stones(setOf(BlackStone(PointAdapter.create('B', 2))))
        val state = BlackWin(stones)
        val point = PointAdapter.create('A', 1)

        //then
        assertThatIllegalStateException().isThrownBy {
            state.put(point)
        }.withMessage("게임이 끝나면 돌을 둘 수 없습니다.")
    }

    @Test
    fun `흑돌이 이긴 상태를 생성할 때 흑돌의 개수가 백돌의 개수보다 1개 더 많지 않으면 에러가 발생한다`() {

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 2)),
                BlackStone(PointAdapter.create('C', 3)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('B', 13))
            )
        )

        //then
        assertThatIllegalArgumentException().isThrownBy {
            BlackWin(stones)
        }.withMessage("흑돌 승리 상태에서는 흑돌의 개수가 백돌의 개수보다 1개 더 많아야 합니다.")
    }
}
