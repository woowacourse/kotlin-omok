package omok.domain

import omok.POINT_H6
import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeOverLine
import omok.domain.grid.Column
import omok.domain.grid.OmokGrid
import omok.domain.grid.OmokPoint
import omok.domain.grid.Row
import omok.getFoulPoint
import omok.omokPoints
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RefereeTest {
    private val referee = Referee()
    private lateinit var grid: OmokGrid

    @BeforeEach
    fun setUp() {
        grid = OmokGrid()
    }

    @Test
    fun `흑돌은 3x3 위치에 돌을 놓을 수 없다`() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(StoneColor.BLACK, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `백돌은 3x3 위치에 돌을 놓을 수 있다`() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(StoneColor.WHITE, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `흑돌은 4x4 위치에 돌을 놓을 수 없다`() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(StoneColor.BLACK, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `백돌은 4x4 위치에 돌을 놓을 수 있다`() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(StoneColor.WHITE, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `흑돌은 장목 위치에 돌을 놓을 수 없다`() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(StoneColor.BLACK, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `백돌은 장목 위치에 돌을 놓을 수 있다`() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(StoneColor.WHITE, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), getFoulPoint())
        }
    }

    @Test
    fun `오목판 밖에 돌을 둘 수 없다`() {
        // given
        val outBoundPoint = OmokPoint(Row(16), Column(16))

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(StoneColor.WHITE, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), outBoundPoint)
        }
    }

    @Test
    fun `이미 돌이 있는 위치에 돌을 놓을 수 없다`() {
        // given
        grid.putStone(OmokPoint(Row(1), Column(2)), StoneColor.BLACK)

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                StoneColor.BLACK,
                grid.getStones(StoneColor.BLACK),
                grid.getStones(StoneColor.WHITE),
                OmokPoint(Row(1), Column(2)),
            )
        }
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                StoneColor.WHITE,
                grid.getStones(StoneColor.BLACK),
                grid.getStones(StoneColor.WHITE),
                OmokPoint(Row(1), Column(2)),
            )
        }
    }

    @Test
    fun `돌 다섯개가 연속으로 이어지면 오목이다`() {
        // given
        val omokStones = omokPoints()
        omokStones.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when
        val actual = referee.checkWin(StoneColor.BLACK, grid.getStones(StoneColor.BLACK), POINT_H6)

        // then
        assertThat(actual).isTrue()
    }
}
