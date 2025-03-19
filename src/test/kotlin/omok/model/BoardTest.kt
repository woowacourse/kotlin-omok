package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `바둑판에 돌을 두면 칸의 상태가 바뀐다`() {
        val board = Board()
        val intersection = Intersection(Position.of(1, 2), IntersectionState.WHITE)
        board.place(intersection)

        val actual: IntersectionState = board.board[1][2]

        val expected: IntersectionState = IntersectionState.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `가로로 완성된 오목을 확인할 수 있다`() {
        val board =
            Board().apply {
                place(Intersection(Position.of(1, 1), IntersectionState.WHITE))
                place(Intersection(Position.of(1, 2), IntersectionState.WHITE))
                place(Intersection(Position.of(1, 3), IntersectionState.WHITE))
                place(Intersection(Position.of(1, 4), IntersectionState.WHITE))
                place(Intersection(Position.of(1, 5), IntersectionState.WHITE))
            }
        val actual: BoardState = board.check(Intersection(Position.of(1, 5), IntersectionState.WHITE))
        val expected: BoardState = BoardState.WHITE_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `대각선으로 완성된 오목을 확인할 수 있다`() {
        val board =
            Board().apply {
                place(Intersection(Position.of(13, 5), IntersectionState.BLACK))
                place(Intersection(Position.of(11, 7), IntersectionState.BLACK))
                place(Intersection(Position.of(10, 8), IntersectionState.BLACK))
                place(Intersection(Position.of(9, 9), IntersectionState.BLACK))
                place(Intersection(Position.of(12, 6), IntersectionState.BLACK))
            }
        val actual: BoardState = board.check(Intersection(Position.of(12, 6), IntersectionState.BLACK))
        val expected: BoardState = BoardState.BLACK_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되지 않았으면 진행 중인 상태이다`() {
        val board =
            Board().apply {
                place(Intersection(Position.of(8, 8), IntersectionState.BLACK))
                place(Intersection(Position.of(8, 9), IntersectionState.WHITE))
                place(Intersection(Position.of(9, 8), IntersectionState.BLACK))
                place(Intersection(Position.of(9, 9), IntersectionState.WHITE))
            }
        val actual: BoardState = board.check(Intersection(Position.of(9, 9), IntersectionState.WHITE))
        val expected: BoardState = BoardState.PLAYING

        assertThat(actual).isEqualTo(expected)
    }
}
