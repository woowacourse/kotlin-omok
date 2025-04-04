package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row

class BoardTest {
    @Test
    fun `바둑판에 돌을 둘 수 있다`() {
        val board = Board().apply { add(Stone(Position(Col(1), Row(1)), Color.BLACK)) }

        val actual: Set<Stone> = board.stones
        val expected: Set<Stone> = setOf(Stone(Position(Col(1), Row(1)), Color.BLACK))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `1 미만의 행에 돌을 둘 수 없다`() {
        val actual: MoveResult = Board().checkRange(Stone(Position(Col(1), Row(0)), Color.BLACK))
        val expected: MoveResult = MoveResult.Failure.StoneNotWithinRow

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `1 미만의 열에 돌을 둘 수 없다`() {
        val actual: MoveResult = Board().checkRange(Stone(Position(Col(0), Row(1)), Color.BLACK))
        val expected: MoveResult = MoveResult.Failure.StoneNotWithinColumn

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `15 초과의 행에 돌을 둘 수 없다`() {
        val actual: MoveResult = Board().checkRange(Stone(Position(Col(1), Row(16)), Color.BLACK))
        val expected: MoveResult = MoveResult.Failure.StoneNotWithinRow

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `15 초과의 열에 돌을 둘 수 없다`() {
        val actual: MoveResult = Board().checkRange(Stone(Position(Col(16), Row(1)), Color.BLACK))
        val expected: MoveResult = MoveResult.Failure.StoneNotWithinColumn

        assertThat(actual).isEqualTo(expected)
    }
}
