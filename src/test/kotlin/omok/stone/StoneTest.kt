package omok.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `돌은 흑 색을 가진다`() {
        val stone = Stone(StoneColor.BLACK, Position(1, 2))

        assertThat(stone.color).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `돌은 백 색을 가진다`() {
        val stone = Stone(StoneColor.WHITE, Position(1, 2))

        assertThat(stone.color).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `돌은 가로 세로 좌표를 가진다`() {
        val stone = Stone(StoneColor.BLACK, Position(1, 2))

        assertThat(stone.position).isEqualTo(Position(1, 2))
    }
}
