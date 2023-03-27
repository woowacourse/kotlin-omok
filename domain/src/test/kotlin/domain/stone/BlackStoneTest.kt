package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BlackStoneTest {
    @Test
    fun `절대적 위치를 가지는 바둑알을 생성할 수 있다`() {

        //given
        val point = PointAdapter.create('A', 1)

        //then
        assertDoesNotThrow {
            BlackStone(point)
        }
    }

    @Test
    fun `같은 좌표값을 가지고 있는 흑돌 객체는 서로 동일하다`() {
        assertThat(
            BlackStone(PointAdapter.create('A', 1))
        ).isEqualTo(BlackStone(PointAdapter.create('A', 1)))
    }
}