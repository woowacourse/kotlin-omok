package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(
            listOf(
                Stone("black", Coordinate(5, "A")),
                Stone("white", Coordinate(8, "H")),
            )
        )
    }

    @Test
    fun `오목판은 판 위에 놓인 돌을 가지고 있다`() {
        assertThat(board.stones.size).isEqualTo(2)
    }

    @Test
    fun `입력받은 좌표가 이미 오목판 위에 돌이 놓인 좌표라면 false를 반환한다`() {
        val input = Coordinate(5, "A")
        val actual = board.checkOccupiedCoordinate(input)

        assertThat(actual).isFalse()
    }

    @Test
    fun `입력받은 좌표가 오목판 위에 돌이 없는 좌표라면 true를 반환한다`() {
        val input = Coordinate(6, "C")
        val actual = board.checkOccupiedCoordinate(input)

        assertThat(actual).isTrue()
    }
}
