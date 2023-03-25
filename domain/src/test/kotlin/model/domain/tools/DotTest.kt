package model.domain.tools

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class DotTest {
    @Test
    fun `index 가 20이고 보드 크기가 15이면 1행 5열이다`() {
        // given
        val index = 20
        val boardSize = 15

        // when
        val dot = Dot.from(index = 20, size = 15)

        // then
        assertAll(
            { assertThat(dot.row).isEqualTo(1) },
            { assertThat(dot.col).isEqualTo(5) }
        )
    }
}
