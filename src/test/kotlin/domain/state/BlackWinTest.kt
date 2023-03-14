package domain.state

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BlackWinTest {

    @Test
    fun `흑돌이 이긴 상태에서 바둑을 두려고 하면 에러가 발생한다`() {
        val state = BlackWin(setOf(Stone(XCoordinate.of('B'), YCoordinate.of(2))), setOf())
        assertThatIllegalStateException().isThrownBy {
            state.put(Stone(XCoordinate.of('A'), YCoordinate.of(1)))
        }.withMessage("게임이 끝나면 돌을 둘 수 없습니다.")
    }

    @Test
    fun `흑돌이 이긴 상태를 생성할 때 흑돌의 개수가 백돌의 개수보다 1개 더 많지 않으면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            BlackWin(
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('C'), YCoordinate.of(3)),
                ),
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                    Stone(XCoordinate.of('B'), YCoordinate.of(13)),
                ),
            )
        }.withMessage("흑돌 승리 상태에서는 흑돌의 개수가 백돌의 개수보다 1개 더 많아야 합니다.")
    }
}
