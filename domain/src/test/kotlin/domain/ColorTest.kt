package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ColorTest {
    @Test
    fun `흑색이 주어졌을 때 백으로 변환한다`() {
        // given
        val blackColor = Color.BLACK

        // when
        val actual = blackColor.turnColor()

        // then
        assertThat(actual).isEqualTo(Color.WHITE)
    }

    @Test
    fun `백색이 주어졌을 때 흑으로 변환한다`() {
        // given
        val whiteColor = Color.WHITE

        // when
        val actual = whiteColor.turnColor()

        // then
        assertThat(actual).isEqualTo(Color.BLACK)
    }
}
