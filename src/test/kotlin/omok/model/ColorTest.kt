package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ColorTest {
    @Test
    fun `BLACK이라면 WHITE를, WHITE이라면 BLACK을 반환할 수 있다`() {
        val color = Color.BLACK
        val expected = Color.WHITE
        val actual = color.change()

        assertThat(expected).isEqualTo(actual)
    }
}