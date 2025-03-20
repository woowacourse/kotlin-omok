package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.point.Point

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    fun `좌표와 돌 상태를 받으면 해당 위치에 돌을 놓는다`() {
        // given
        val row = 1
        val col = 2
        // when
        omokGrid.putStone(Point(row, col), StoneState.BLACK)
        // then
        assertThat(omokGrid.board[row][col]).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        // given
        val row = 1
        val col = 2
        // when
        omokGrid.putStone(Point(row, col), StoneState.BLACK)
        // then
        assertThrows<IllegalStateException> {
            omokGrid.putStone(Point(row, col), StoneState.BLACK)
        }
    }

    @Test
    fun `칸이 모두 채워지면 true를 반환한다`() {
        // given & when
        val omokGrid = OmokGrid(List(15) { MutableList(15) { StoneState.WHITE } })
        // then
        assertThat(omokGrid.isFull()).isTrue()
    }
}
