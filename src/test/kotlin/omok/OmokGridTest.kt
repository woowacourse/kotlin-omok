package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    fun `보드는 15x15 크기를 가진다`() {
        assertThat(omokGrid.board.size).isEqualTo(15)
        assertThat(omokGrid.board.first().size).isEqualTo(15)
    }

    @Test
    fun `좌표와 돌 상태를 받으면 해당 위치에 돌을 놓는다`() {
        val row = 1
        val col = 2
        omokGrid.putStone(row, col, StoneState.BLACK)

        assertThat(omokGrid.board[row][col].state).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        val row = 1
        val col = 2
        omokGrid.putStone(row, col, StoneState.BLACK)

        assertThrows<IllegalStateException> {
            omokGrid.putStone(row, col, StoneState.BLACK)
        }
    }
}
