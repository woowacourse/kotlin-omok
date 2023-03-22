package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WhiteStoneTest {
    @Test
    fun `x좌표와 y좌표로 바둑알을 생성할 수 있다`() {
        assertDoesNotThrow {
            WhiteStone(PointAdapter.create('A', 2))
        }
    }

    @Test
    fun `같은 좌표값을 가지고 있는 백돌 객체는 서로 동일하다`() {
        assertThat(WhiteStone(PointAdapter.create('A', 1))).isEqualTo(WhiteStone(PointAdapter.create('A', 1)))
    }
}