package domain

import domain.domain.BoardState
import domain.library.cldhfleks2.SubdivideRuleAdapter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BoardTest {
    @Test
    fun `돌을 놓고 해당 좌표에 돌이 들어갔는지 확인`() {
        val board = Board()
        board.addStone(CoordinateState.BLACK, Position(2, 4))

        assertThat(board.boardState.value[2][4]).isEqualTo(CoordinateState.BLACK)
    }

    @Test
    fun `돌을 놓으면 마지막 돌의 위치가 돌이 놓인 좌표로 변경된다`() {
        val board = Board()
        val targetPosition = Position(2, 4)
        board.addStone(CoordinateState.BLACK, targetPosition)

        assertThat(board.lastPosition).isEqualTo(targetPosition)
    }
}
