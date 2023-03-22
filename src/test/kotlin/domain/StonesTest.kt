package domain

import domain.CoordinateState.BLACK
import domain.domain.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StonesTest {
    @Test
    fun `돌을 놓고 해당 좌표에 돌이 들어갔는지 확인`() {
        val stones = Stones()
        stones.addStone(Position(2, 4), BLACK)

        assertThat(stones[2, 4]).isEqualTo(BLACK)
    }

    @Test
    fun `돌을 놓으면 마지막 위치가 돌이 놓인 좌표로 변경된다`() {
        val stones = Stones()
        val targetPosition = Position(2, 4)
        stones.addStone(targetPosition, BLACK)

        assertThat(stones.lastPosition).isEqualTo(targetPosition)
    }
}
