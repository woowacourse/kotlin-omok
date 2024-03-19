package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `stone 을 하나 추가할 경우, Stones 의 사이즈는 1 증가한다`() {
        // when
        val stones = Stones()
        assertThat(stones.stones.size).isEqualTo(0)

        // given
        stones.add(Stone("A", 1))

        // then
        assertThat(stones.stones.size).isEqualTo(1)
    }

    @Test
    fun `Stones 의 마지막 스톤을 확인할 수 있다`() {
        val stones = Stones()
        stones.add(H_1)
        stones.add(H_2)

        assertThat(stones.lastStone()).isEqualTo(H_2)
    }

    companion object {
        private val H_1 = Stone("H", 1)
        private val H_2 = Stone("H", 2)
    }
}
