package omok.domain.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StoneStateTest {
    @Test
    fun `흰돌이 있다`() {
        val stoneState = WhiteStoneState
        assertThat(stoneState).isEqualTo(WhiteStoneState)
    }

    @Test
    fun `검은돌이 있다`() {
        val stoneState = BlackStoneState
        assertThat(stoneState).isEqualTo(BlackStoneState)
    }

    @Test
    fun `빈 돌을 표현 할 수 있다`() {
        val stoneState = EmptyStoneState
        assertThat(stoneState).isEqualTo(EmptyStoneState)
    }

    @Test
    fun `검은 돌 다음은 흰돌이다`() {
        val stoneState = BlackStoneState
        assertThat(stoneState.next()).isEqualTo(WhiteStoneState)
    }

    @Test
    fun `흰 돌 다음은 검은 돌이다`() {
        val stoneState = WhiteStoneState
        assertThat(stoneState.next()).isEqualTo(BlackStoneState)
    }

    @Test
    fun `빈 돌 다음은 에러가 발생한다`() {
        val stoneState = EmptyStoneState
        assertThrows<IllegalStateException> { stoneState.next() }
    }

    @Test
    fun `모든 흰돌은 같은 객체다`() {
        val stoneState = WhiteStoneState
        assertThat(stoneState).isSameAs(WhiteStoneState)
    }

    @Test
    fun `모든 검은돌은 같은 객체다`() {
        val stoneState = BlackStoneState
        assertThat(stoneState).isSameAs(BlackStoneState)
    }

    @Test
    fun `모든 빈돌은 같은 객체다`() {
        val stoneState = EmptyStoneState
        assertThat(stoneState).isSameAs(EmptyStoneState)
    }
}
