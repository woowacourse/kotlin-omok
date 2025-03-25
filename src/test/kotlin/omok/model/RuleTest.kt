package omok.model

import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RuleTest {
    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(Col(4), Row(4)), Color.BLACK))
                add(Stone(Position(Col(5), Row(4)), Color.BLACK))
                add(Stone(Position(Col(6), Row(5)), Color.BLACK))
                add(Stone(Position(Col(5), Row(6)), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(Col(7), Row(4)), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.DoubleThreeViolation

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(Col(12), Row(3)), Color.BLACK))
                add(Stone(Position(Col(12), Row(4)), Color.BLACK))
                add(Stone(Position(Col(12), Row(7)), Color.BLACK))
                add(Stone(Position(Col(12), Row(9)), Color.BLACK))
                add(Stone(Position(Col(12), Row(10)), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(Col(12), Row(6)), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.DoubleFourViolation

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(Col(15), Row(3)), Color.BLACK))
                add(Stone(Position(Col(14), Row(3)), Color.BLACK))
                add(Stone(Position(Col(12), Row(3)), Color.BLACK))
                add(Stone(Position(Col(11), Row(3)), Color.BLACK))
                add(Stone(Position(Col(10), Row(3)), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(Col(13), Row(3)), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.OverlineViolation

        assertThat(actual).isEqualTo(expected)
    }
}
