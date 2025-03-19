package omok.model.domain.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokBoardTest {
    @Test
    fun `오목판은 15 x 15 사이즈이다`() {
        // given
        val actual = PlayingBoard().board.value

        // when & then
        (1..15).forEach { row ->
            (1..15).forEach { column ->
                assertThat(actual).contains(Point(Position(RowPosition(row), ColumnPosition(column))))
            }
        }
        assertThat(actual.size).isEqualTo(15 * 15)
    }
}
