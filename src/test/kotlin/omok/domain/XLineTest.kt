package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XLineTest {
    @Test
    fun `x의 좌표들을 가지고 있다`() {
        val xLine = XLine().keys.map { it.value }
        val expected = ('A'..'O').toList()
        assertThat(xLine).isEqualTo(expected)
    }

    @Test
    fun `초기 x의 좌표들은 모두 비어있다`() {
        val xLine = XLine().values
        assertThat(xLine).allMatch { it == StoneState.EMPTY }
    }
}
