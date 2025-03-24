package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RuleTest {
    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(4, 4), Color.BLACK))
                add(Stone(Position(5, 4), Color.BLACK))
                add(Stone(Position(6, 5), Color.BLACK))
                add(Stone(Position(5, 6), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(7, 4), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.DoubleThreeViolation

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(12, 3), Color.BLACK))
                add(Stone(Position(12, 4), Color.BLACK))
                add(Stone(Position(12, 7), Color.BLACK))
                add(Stone(Position(12, 9), Color.BLACK))
                add(Stone(Position(12, 10), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(12, 6), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.DoubleFourViolation

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone(Position(15, 3), Color.BLACK))
                add(Stone(Position(14, 3), Color.BLACK))
                add(Stone(Position(12, 3), Color.BLACK))
                add(Stone(Position(11, 3), Color.BLACK))
                add(Stone(Position(10, 3), Color.BLACK))
            }

        val actual: MoveResult = Rule().checkViolation(board, Position(13, 3), Color.BLACK)
        val expected: MoveResult = MoveResult.Failure.OverlineViolation

        assertThat(actual).isEqualTo(expected)
    }
}
