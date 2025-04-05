package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `돌에는 흑과 백이 있다`() {
        assertThat(Stone.entries).hasSameElementsAs(listOf(Stone.BLACK, Stone.WHITE))
    }
}
