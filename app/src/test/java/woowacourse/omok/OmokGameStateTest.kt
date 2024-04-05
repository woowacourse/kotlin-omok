package woowacourse.omok

import omok.model.OmokGameState
import omok.model.entity.Point
import omok.model.entity.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameStateTest {
    @Test
    fun `오목은 검은색 돌부터 둔다`() {
        val actual = OmokGameState().turn.color()
        val expected = StoneColor.BLACK
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `여러 턴을 한 번에 진행할 수 있다`() {
        val points = listOf(Point(1, 1), Point(2, 2), Point(3, 3))
        val actual = OmokGameState().runMultiple(points).turn.color()
        val expected = StoneColor.WHITE
        assertThat(actual).isEqualTo(expected)
    }
}
