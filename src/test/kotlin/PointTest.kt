import PointState.EMPTY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun `초기 state는 EMPTY다`() {
        // given & when
        val actual = Point(POSITION_A_ZERO).state
        val expected = EMPTY
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
