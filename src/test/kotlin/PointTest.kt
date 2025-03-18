import PointState.EMPTY
import PointState.OCCUPIED_BLACK
import PointState.OCCUPIED_WHITE
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

    @Test
    fun `검은돌을 두면 state가 검정색으로 바뀐다`() {
        // given
        val point = Point(POSITION_A_ZERO)
        // when
        point.placeStone(Stone.BLACK)
        val actual = point.state
        val expected = OCCUPIED_BLACK
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌을 두면 state가 흰색으로 바뀐다`() {
        // given
        val point = Point(POSITION_A_ZERO)
        // when
        point.placeStone(Stone.WHITE)
        val actual = point.state
        val expected = OCCUPIED_WHITE
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
