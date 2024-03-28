package woowacourse.omok.kotlin.omok.model.position

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.src.main.kotlin.omok.model.position.RelativeDirection

class RelativeDirectionTest {
    @Test
    fun `돌의 위치 값이 반전되는지 확인한다`() {
        // given
        val position = RelativeDirection(1, 1)

        // when
        val actual = -position
        val expected = RelativeDirection(-1, -1)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
