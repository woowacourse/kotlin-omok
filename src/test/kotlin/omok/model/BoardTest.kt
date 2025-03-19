package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class BoardTest {
    @Test
    fun `바둑판에 돌을 두면 칸의 상태가 바뀐다`() {
        val board = Board()
        val intersection = Intersection(Point(1, 2), IntersectionState.WHITE)
        board.place(intersection, WhiteRenjuRule())

        val actual: IntersectionState = board.board[1][2]

        val expected: IntersectionState = IntersectionState.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `가로로 완성된 오목을 확인할 수 있다`() {
        val whiteRule = WhiteRenjuRule()
        val board =
            Board().apply {
                place(Intersection(Point(1, 1), IntersectionState.WHITE), whiteRule)
                place(Intersection(Point(1, 2), IntersectionState.WHITE), whiteRule)
                place(Intersection(Point(1, 3), IntersectionState.WHITE), whiteRule)
                place(Intersection(Point(1, 4), IntersectionState.WHITE), whiteRule)
            }
        val actual: BoardState = board.place(Intersection(Point(1, 5), IntersectionState.WHITE), whiteRule)
        val expected: BoardState = BoardState.WHITE_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `대각선으로 완성된 오목을 확인할 수 있다`() {
        val blackRule = BlackRenjuRule()
        val board =
            Board().apply {
                place(Intersection(Point(13, 5), IntersectionState.BLACK), blackRule)
                place(Intersection(Point(11, 7), IntersectionState.BLACK), blackRule)
                place(Intersection(Point(10, 8), IntersectionState.BLACK), blackRule)
                place(Intersection(Point(9, 9), IntersectionState.BLACK), blackRule)
            }
        val actual: BoardState = board.place(Intersection(Point(12, 6), IntersectionState.BLACK), blackRule)
        val expected: BoardState = BoardState.BLACK_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되지 않았으면 진행 중인 상태이다`() {
        val whiteRule = WhiteRenjuRule()
        val blackRule = BlackRenjuRule()
        val board =
            Board().apply {
                place(Intersection(Point(8, 8), IntersectionState.BLACK), blackRule)
                place(Intersection(Point(8, 9), IntersectionState.WHITE), whiteRule)
                place(Intersection(Point(9, 8), IntersectionState.BLACK), blackRule)
            }
        val actual: BoardState = board.place(Intersection(Point(9, 9), IntersectionState.WHITE), whiteRule)
        val expected: BoardState = BoardState.PLAYING

        assertThat(actual).isEqualTo(expected)
    }
}
