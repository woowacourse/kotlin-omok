package omok.domain

import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeOverLine
import omok.toViolation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.BlackRenjuRule
import rule.wrapper.point.Point

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
        omokGrid.putStone(Point(row, col), StoneState.BLACK)

        assertThat(omokGrid.board[row][col]).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        val row = 1
        val col = 2
        omokGrid.putStone(Point(row, col), StoneState.BLACK)

        assertThrows<IllegalStateException> {
            omokGrid.putStone(Point(row, col), StoneState.BLACK)
        }
    }

    @Test
    fun `칸이 모두 채워지면 true를 반환한다`() {
        val omokGrid = OmokGrid(List(15) { MutableList(15) { StoneState.WHITE } })
        assertThat(omokGrid.isFull()).isTrue()
    }

    @Test
    fun `해당 색깔의 돌 위치 리스트를 반환한다`() {
        val omokGrid = OmokGrid()
        omokGrid.putStone(Point(1, 1), StoneState.BLACK)
        omokGrid.putStone(Point(2, 2), StoneState.BLACK)
        omokGrid.putStone(Point(3, 3), StoneState.BLACK)
        omokGrid.putStone(Point(4, 4), StoneState.BLACK)

        val actual = omokGrid.findStones(StoneState.BLACK)
        val expected = listOf(Point(1, 1), Point(2, 2), Point(3, 3), Point(4, 4))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `삼삼일 때 렌주룰을 위반한다`() {
        val omokGrid = OmokGrid()
        val beforeDoubleThree = beforeDoubleThree()

        beforeDoubleThree.forEach { point -> omokGrid.putStone(point, StoneState.BLACK) }

        val actual = omokGrid.isViolation(BlackRenjuRule(), toViolation())

        assertThat(actual).isTrue()
    }

    @Test
    fun `사사일 때 렌주룰을 위반한다`() {
        val omokGrid = OmokGrid()
        val beforeDoubleThree = beforeDoubleFour()

        beforeDoubleThree.forEach { point -> omokGrid.putStone(point, StoneState.BLACK) }

        val actual = omokGrid.isViolation(BlackRenjuRule(), toViolation())

        assertThat(actual).isTrue()
    }

    @Test
    fun `장목일 때 렌주룰을 위반한다`() {
        val omokGrid = OmokGrid()
        val beforeDoubleThree = beforeOverLine()

        beforeDoubleThree.forEach { point -> omokGrid.putStone(point, StoneState.BLACK) }

        val actual = omokGrid.isViolation(BlackRenjuRule(), toViolation())

        assertThat(actual).isTrue()
    }
}
