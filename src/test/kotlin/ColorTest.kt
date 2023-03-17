import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class ColorTest {
    @Test
    fun `컬러가 반전이 된다`() {
        assertAll(
            "컬러가 반전이 된다",
            {
                val color = Color.BLACK
                val actual = !color
                val expected = Color.WHITE
                assertThat(actual).isEqualTo(expected)
            },
            {
                val color = Color.WHITE
                val actual = !color
                val expected = Color.BLACK
                assertThat(actual).isEqualTo(expected)
            }
        )
    }
}
