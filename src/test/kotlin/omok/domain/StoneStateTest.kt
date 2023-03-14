package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneStateTest {
    @Test
    fun `흰돌이 있다`() {
        val stoneState = StoneState.WHITE
        assertThat(stoneState).isEqualTo(StoneState.WHITE)
    }

    @Test
    fun `검은돌이 있다`() {
        val stoneState = StoneState.BLACK
        assertThat(stoneState).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `빈 곳을 표현 할 수 있다`() {
        val stoneState = StoneState.EMPTY
        assertThat(stoneState).isEqualTo(StoneState.EMPTY)
    }
}
