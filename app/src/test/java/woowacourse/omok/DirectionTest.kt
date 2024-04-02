package omock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.search.Direction

class DirectionTest {
    @Test
    fun `Direcation의 reverse를 통해서 반대 방향을 확인한다`() {
        assertThat(Direction.reverse(Direction.TOP)).isEqualTo(Direction.BOTTOM)
        assertThat(Direction.reverse(Direction.TOP_RIGHT)).isEqualTo(Direction.BOTTOM_LEFT)
        assertThat(Direction.reverse(Direction.RIGHT)).isEqualTo(Direction.LEFT)
        assertThat(Direction.reverse(Direction.RIGHT_BOTTOM)).isEqualTo(Direction.LEFT_TOP)
        assertThat(Direction.reverse(Direction.BOTTOM)).isEqualTo(Direction.TOP)
        assertThat(Direction.reverse(Direction.BOTTOM_LEFT)).isEqualTo(Direction.TOP_RIGHT)
        assertThat(Direction.reverse(Direction.LEFT)).isEqualTo(Direction.RIGHT)
        assertThat(Direction.reverse(Direction.LEFT_TOP)).isEqualTo(Direction.RIGHT_BOTTOM)
    }
}
