package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `흑돌의 다음 순서는 백돌이다`() {
        val actual = Stone.BLACK.next()
        assertThat(actual).isEqualTo(Stone.WHITE)
    }

    @Test
    fun `백돌의 다음 순서는 흑돌이다`() {
        val actual = Stone.WHITE.next()
        assertThat(actual).isEqualTo(Stone.BLACK)
    }
}
