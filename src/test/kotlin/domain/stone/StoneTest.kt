package domain.stone

import domain.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `오목알은 자신의 위치를 행과 열로 상태를 가지고 있다`() {
        val stone = Stone.of(10, 10)
        assertThat(stone.position).isEqualTo(Position(10, 10))
    }
}
