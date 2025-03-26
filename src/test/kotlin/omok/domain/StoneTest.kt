package omok.domain

import omok.Black
import omok.White
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `오목돌은 row 위치를 가진다`() {
        val stone = Stone(Position(0, 0), Black)
        assertThat(stone.position.row).isEqualTo(0)
    }

    @Test
    fun `오목돌은 colomn 위치를 가진다`() {
        val stone = Stone(Position(0, 0), Black)
        assertThat(stone.position.column).isEqualTo(0)
    }

    @Test
    fun `오목돌은 색상을 가진다`() {
        val stone = Stone(Position(0, 0), White)
        assertThat(stone.color).isEqualTo(StoneType.WHITE)
    }
}
