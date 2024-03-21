package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
        resetPosition(Board, "lastPosition")
    }

    @Test
    fun `마지막 돌이 없다면 null을 반환한다`() {
        val actual = Board.getLastStonePosition()
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌이 있다면 돌의 위치를 반환한다`() {
        val stone = BlackStone()
        stone.putStone(Position(1, 5))
        val actual = Board.getLastStonePosition()
        val expected = Position(1, 5)
        assertThat(actual).isEqualTo(expected)
    }

    private fun resetPosition(
        target: Any,
        fieldName: String,
    ) {
        val field = target.javaClass.getDeclaredField(fieldName)

        with(field) {
            isAccessible = true
            set(target, null)
        }
    }
}
