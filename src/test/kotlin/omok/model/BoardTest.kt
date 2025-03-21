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

    @Test
    fun `바둑판에 돌을 두면 마지막 돌이 갱신된다`() {
        val board = Board().apply { add(Stone2(Position(1, 1), Color.BLACK)) }

        val actual: Stone2? = board.lastStone
        val expected = Stone2(Position(1, 1), Color.BLACK)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone2(Position(4, 4), Color.BLACK))
                add(Stone2(Position(5, 4), Color.BLACK))
                add(Stone2(Position(6, 5), Color.BLACK))
                add(Stone2(Position(5, 6), Color.BLACK))
            }

        assertThrows<IllegalArgumentException> { board.add(Stone2(Position(7, 4), Color.BLACK)) }
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone2(Position(12, 3), Color.BLACK))
                add(Stone2(Position(12, 4), Color.BLACK))
                add(Stone2(Position(12, 9), Color.BLACK))
                add(Stone2(Position(12, 10), Color.BLACK))
                add(Stone2(Position(12, 7), Color.BLACK))
            }

        assertThrows<IllegalArgumentException> { board.add(Stone2(Position(12, 6), Color.BLACK)) }
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val board =
            Board().apply {
                add(Stone2(Position(15, 3), Color.BLACK))
                add(Stone2(Position(14, 3), Color.BLACK))
                add(Stone2(Position(12, 3), Color.BLACK))
                add(Stone2(Position(11, 3), Color.BLACK))
                add(Stone2(Position(10, 3), Color.BLACK))
            }

        assertThrows<IllegalArgumentException> { board.add(Stone2(Position(13, 3), Color.BLACK)) }
    }
}
