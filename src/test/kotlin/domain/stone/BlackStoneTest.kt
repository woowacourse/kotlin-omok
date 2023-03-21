package domain.stone

import domain.stone.BlackStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BlackStoneTest {
    @Test
    fun `x좌표와 y좌표로 바둑알을 생성할 수 있다`() {
        assertDoesNotThrow {
            BlackStone('A', 1)
        }
    }

    @Test
    fun `같은 좌표값을 가지고 있는 흑돌 객체는 서로 동일하다`(){
        assertThat(BlackStone('A', 1)).isEqualTo(BlackStone('A', 1))
    }
}