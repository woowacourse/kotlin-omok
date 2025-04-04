import woowacourse.omok.model.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StoneColorTest {
    @Test
    fun `색깔을 서로 스위치 할 수 있다`() {
        // given+when
        val stoneColorWhite = StoneColor.WHITE
        val stoneColorBlack = StoneColor.BLACK
        // result
        assertAll(
            { assertThat(stoneColorWhite.switch()).isEqualTo(StoneColor.BLACK) },
            { assertThat(stoneColorBlack.switch()).isEqualTo(StoneColor.WHITE) },
        )
    }
}
