package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `바둑판에 돌을 둘 수 있다`() {
        val board = Board().apply { add(Stone(Position(1, 1), Color.BLACK)) }

        val actual: Set<Stone> = board.stones
        val expected: Set<Stone> = setOf(Stone(Position(1, 1), Color.BLACK))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 돌이 있는 자리에는 돌을 둘 수 없다`() {
        val board = Board().apply { add(Stone(Position(1, 1), Color.BLACK)) }

        val actual: MoveResult = board.add(Stone(Position(1, 1), Color.BLACK))
        val expected = MoveResult.Fail.StoneAlreadyPlaced

        assertThat(actual).isEqualTo(expected)
    }
}
