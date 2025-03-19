package omok.domain

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class StoneTest {
    @Test
    fun `오목은 row 위치를 가진다`() {
        val stone = Stone(0,0)
        assertThat(stone.col).isEqualTo(0)
    }

    @Test
    fun `오목은 col 위치를 가진다`() {
        val stone = Stone(0,0)
        assertThat(stone.col).isEqualTo(0)
    }
}