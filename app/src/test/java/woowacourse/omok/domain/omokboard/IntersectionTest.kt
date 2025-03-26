package woowacourse.omok.domain.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.player.StoneColor

class IntersectionTest {
    @Test
    fun `초기 state는 EMPTY다`() {
        // given & when
        val actual = Intersection().state
        val expected = IntersectionState.EMPTY

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌을 두면 state가 검정색으로 바뀐다`() {
        // given
        val intersection = Intersection()

        // when
        intersection.updateState(StoneColor.BLACK)
        val actual = intersection.state
        val expected = IntersectionState.OCCUPIED_BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌을 두면 state가 흰색으로 바뀐다`() {
        // given
        val intersection = Intersection()

        // when
        intersection.updateState(StoneColor.WHITE)
        val actual = intersection.state
        val expected = IntersectionState.OCCUPIED_WHITE

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
