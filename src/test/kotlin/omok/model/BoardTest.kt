package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `바둑판에 돌을 둘 수 있다`() {
        val board = Board().apply { add(Stone2(Position(1, 1), Color.BLACK)) }

        val actual: Set<Stone2> = board.stones
        val expected: Set<Stone2> = setOf(Stone2(Position(1, 1), Color.BLACK))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 돌이 있는 자리에는 돌을 둘 수 없다`() {
        val board = Board().apply { add(Stone2(Position(1, 1), Color.BLACK)) }

        assertThrows<IllegalArgumentException> { board.add(Stone2(Position(1, 1), Color.WHITE)) }
    }
}
