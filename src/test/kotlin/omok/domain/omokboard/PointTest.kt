package omok.domain.omokboard

import omok.domain.omokboard.PointState.EMPTY
import omok.domain.omokboard.PointState.OCCUPIED_BLACK
import omok.domain.omokboard.PointState.OCCUPIED_WHITE
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun `초기 state는 EMPTY다`() {
        // given & when
        val actual = Point().state
        val expected = EMPTY
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌을 두면 state가 검정색으로 바뀐다`() {
        // given
        val point = Point()
        // when
        point.updateState(StoneColor.BLACK)
        val actual = point.state
        val expected = OCCUPIED_BLACK
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌을 두면 state가 흰색으로 바뀐다`() {
        // given
        val point = Point()
        // when
        point.updateState(StoneColor.WHITE)
        val actual = point.state
        val expected = OCCUPIED_WHITE
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
