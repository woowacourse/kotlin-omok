package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.POINT_H6
import woowacourse.omok.beforeDoubleFour
import woowacourse.omok.beforeDoubleThree
import woowacourse.omok.beforeOverLine
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.rule.RenjuRuleAdapterImpl
import woowacourse.omok.getFoulPoint
import woowacourse.omok.omokPoints

class RefereeTest {
    private val referee = Referee()
    private lateinit var grid: OmokGrid

    @BeforeEach
    fun setUp() {
        grid = OmokGrid()
    }

    @Test
    @DisplayName("흑돌은 3x3 위치에 돌을 놓을 수 없다")
    fun validateDoubleThreeWithBlackStone() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("백돌은 3x3 위치에 돌을 놓을 수 있다")
    fun validateDoubleThreeWithWhiteStone() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(
                WhiteRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("흑돌은 4x4 위치에 돌을 놓을 수 없다")
    fun validateDoubleFourWithBlackStone() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("백돌은 4x4 위치에 돌을 놓을 수 있다")
    fun validateDoubleFourWithWhiteStone() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(
                WhiteRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("흑돌은 장목 위치에 돌을 놓을 수 없다")
    fun validateOverLineWithBlackStone() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("백돌은 장목 위치에 돌을 놓을 수 있다")
    fun validateOverLineWithWhiteStone() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(it, StoneColor.WHITE)
        }

        // when & then
        assertDoesNotThrow {
            referee.checkViolation(
                WhiteRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                getFoulPoint(),
            )
        }
    }

    @Test
    @DisplayName("이미 돌이 있는 위치에 돌을 놓을 수 없다")
    fun validateOccupied() {
        // given
        grid.putStone(OmokPoint(Row(1), Column(2)), StoneColor.BLACK)

        // when & then
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                OmokPoint(Row(1), Column(2)),
            )
        }
        assertThrows<IllegalStateException> {
            referee.checkViolation(
                WhiteRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                OmokPoint(Row(1), Column(2)),
            )
        }
    }

    @Test
    @DisplayName("돌 다섯개가 연속으로 이어지면 오목이다")
    fun checkWin() {
        // given
        val omokStones = omokPoints()
        omokStones.forEach {
            grid.putStone(it, StoneColor.BLACK)
        }

        // when
        val actual = referee.checkWin(RenjuRuleAdapterImpl, grid.getStonesByColor(StoneColor.BLACK), POINT_H6)

        // then
        assertThat(actual).isTrue()
    }
}
