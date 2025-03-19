package omok.domain

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class StoneTest {
    @Test
    fun `오목돌은 row 위치를 가진다`() {
        val stone = Stone(0,0, StoneColor.BLACK)
        assertThat(stone.row).isEqualTo(0)
    }

    @Test
    fun `오목돌은 col 위치를 가진다`() {
        val stone = Stone(0, 0, StoneColor.BLACK)
        assertThat(stone.column).isEqualTo(0)
    }

    @Test
    fun `오목돌은 색상을 가진다`() {
        val stone = Stone(0, 0, StoneColor.WHITE)

    }
}