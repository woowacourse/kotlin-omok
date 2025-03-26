package omok.model.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.PointState

class PointTest {
    private lateinit var point: Point

    @BeforeEach
    fun setUp() {
        point = Point(1, 1)
    }

    @Test
    fun `point의 초기 상태는 OPEN이다`() {
        assertThat(point.state).isEqualTo(PointState.OPEN)
    }

    @Test
    fun `흑돌을 둔 경우에는 point의 상태가 BLACK이 된다`() {
        point.changeState(StoneColor.BLACK)
        assertThat(point.state).isEqualTo(PointState.BLACK)
    }

    @Test
    fun `백돌을 둔 경우에는 point의 상태가 WHITE가 된다`() {
        point.changeState(StoneColor.WHITE)
        assertThat(point.state).isEqualTo(PointState.WHITE)
    }
}
