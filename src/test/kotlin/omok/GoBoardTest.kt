package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GoBoardTest {
    @Test
    fun `바둑판에 돌을 두면 칸의 상태가 바뀐다`() {
        val board = Board()
        val intersection = Intersection(Position(Line(1), Line(2)), IntersectionState.WHITE)
        board.place(intersection)

        val actual: IntersectionState = board.board[1][2]

        val expected: IntersectionState = IntersectionState.WHITE

        assertThat(actual).isEqualTo(expected)
    }
}
