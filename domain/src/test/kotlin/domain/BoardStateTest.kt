package domain

import domain.domain.BoardState
import domain.domain.CoordinateState
import domain.domain.Position
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BoardStateTest {
    @Test
    fun `해당 좌표가 비어있다면 True를 반환한다`() {
        val temp = List(14) { MutableList(14) { CoordinateState.EMPTY } }
        temp[1][1] = CoordinateState.BLACK
        val boardState = BoardState(temp)

        Assertions.assertThat(boardState.isEmpty(Position(1, 1))).isFalse
    }

    @Test
    fun `해당 좌표에 돌이 있다면 False를 반환한다`() {
        val temp = List(14) { MutableList(14) { CoordinateState.EMPTY } }
        val boardState = BoardState(temp)

        Assertions.assertThat(boardState.isEmpty(Position(1, 1))).isTrue
    }

    @Test
    fun `돌을 놓고 해당 좌표에 돌이 들어갔는지 확인`() {
        val board = BoardState()
        board.addStone(CoordinateState.BLACK, Position(2, 4))

        Assertions.assertThat(board.value[2][4]).isEqualTo(CoordinateState.BLACK)
    }
}
