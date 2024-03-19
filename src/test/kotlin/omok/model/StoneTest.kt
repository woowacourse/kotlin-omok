package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `돌은 흑과 백으로 구분 한다`() {
        val blackStone = Stone("black")
        val whiteStone = Stone("white")

        assertThat(blackStone.color).isEqualTo("black")
        assertThat(whiteStone.color).isEqualTo("white")
    }
}
