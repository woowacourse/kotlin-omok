package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        assertThrows<IllegalArgumentException> { Board().add(Stone(Position(Col(0), Row(1)), Color.BLACK)) }
    }

    @Test
    fun `1 미만의 열에 돌을 둘 수 없다`() {
        assertThrows<IllegalArgumentException> { Board().add(Stone(Position(Col(1), Row(0)), Color.BLACK)) }
    }

    @Test
    fun `15 초과의 행에 돌을 둘 수 없다`() {
        assertThrows<IllegalArgumentException> { Board().add(Stone(Position(Col(1), Row(16)), Color.BLACK)) }
    }

    @Test
    fun `15 초과의 열에 돌을 둘 수 없다`() {
        assertThrows<IllegalArgumentException> { Board().add(Stone(Position(Col(1), Row(16)), Color.BLACK)) }
    }

    @Test
    fun `이미 돌이 있는 자리에는 돌을 둘 수 없다`() {
        val board = Board().apply { add(Stone(Position(Col(1), Row(1)), Color.BLACK)) }

        val actual: MoveResult = board.add(Stone(Position(Col(1), Row(1)), Color.BLACK))
        val expected = MoveResult.Failure.PositionAlreadyOccupied

        assertThat(actual).isEqualTo(expected)
    }
}
