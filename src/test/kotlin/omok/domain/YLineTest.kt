package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class YLineTest {
    @Test
    fun `y의 좌표들을 가지고 있다`() {
        val yLine = YLine().keys.map { it.value }
        val expected = (1..15).toList()
        assertThat(yLine).isEqualTo(expected)
    }

    @Test
    fun `y의 각자 좌표들에는 XLine이 있다`() {
        val yLine = YLine().values
        assertThat(yLine).allMatch { xLine ->
            xLine.values.filterNot { it == StoneState.EMPTY }.isEmpty()
        }
    }
}
