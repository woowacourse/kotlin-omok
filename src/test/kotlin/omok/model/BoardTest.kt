package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `바둑판에 돌을 둘 수 있다`() {
        val board = Board().apply { add(Stone2(Position(1, 1), StoneColor.BLACK)) }

        val actual = board.stones
        val expected = setOf(Stone2(Position(1, 1), StoneColor.BLACK))

        assertThat(actual).isEqualTo(expected)
    }
}
