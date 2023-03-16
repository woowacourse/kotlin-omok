package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BoardTest {
    @Test
    fun `돌을 놓고 해당 좌표에 돌이 들어갔는지 확인`() {
        val board = Board()
        board.addStone(CoordinateState.BLACK, Position(Coordinate(2), Coordinate(4)))

        assertThat(board.board[2][4]).isEqualTo(CoordinateState.BLACK)
    }

    @Test
    fun `돌을 놓으면 lastPosition이 돌이 놓인 좌표로 변경된다`() {
        val board = Board()
        val targetPosition = Position(Coordinate(2), Coordinate(4))
        board.addStone(CoordinateState.BLACK, targetPosition)

        assertThat(board.lastPosition).isEqualTo(targetPosition)
    }

    @Test
    fun `해당 좌표가 비어있다면 True를 반환한다`() {
        val temp = List(14) { MutableList(14) { CoordinateState.EMPTY } }
        temp[1][1] = CoordinateState.BLACK
        val board = Board(temp)

        assertThat(board.isEmpty(Position(Coordinate(1), Coordinate(1)))).isFalse
    }

    @Test
    fun `해당 좌표에 돌이 있다면 False를 반환한다`() {
        val temp = List(14) { MutableList(14) { CoordinateState.EMPTY } }
        val board = Board(temp)

        assertThat(board.isEmpty(Position(Coordinate(1), Coordinate(1)))).isTrue
    }
}
