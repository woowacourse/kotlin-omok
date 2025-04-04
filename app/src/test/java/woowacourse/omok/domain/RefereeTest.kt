package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.POINT_H6
import woowacourse.omok.beforeDoubleFour
import woowacourse.omok.beforeDoubleThree
import woowacourse.omok.beforeOverLine
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone
import woowacourse.omok.domain.rule.RenjuRuleAdapterImpl
import woowacourse.omok.domain.rule.ValidationResult
import woowacourse.omok.getFoulPoint
import woowacourse.omok.omokPoints

class RefereeTest {
    private val referee = Referee()
    private lateinit var grid: OmokGrid

    @BeforeEach
    fun setUp() {
        grid = OmokGrid(setOf())
    }

    @Test
    @DisplayName("흑돌은 3x3 위치에 돌을 놓을 수 없다")
    fun validateDoubleThreeWithBlackStone() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(Stone(it, StoneColor.BLACK))
        }

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.BLACK),
            )
        val expected = ValidationResult.Failure.DoubleThree

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("백돌은 3x3 위치에 돌을 놓을 수 있다")
    fun validateDoubleThreeWithWhiteStone() {
        // given
        val beforeDoubleThree = beforeDoubleThree()
        beforeDoubleThree.forEach {
            grid.putStone(Stone(it, StoneColor.WHITE))
        }

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.WHITE),
            )
        val expected = ValidationResult.Success

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("흑돌은 4x4 위치에 돌을 놓을 수 없다")
    fun validateDoubleFourWithBlackStone() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(Stone(it, StoneColor.BLACK))
        }

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.BLACK),
            )
        val expected = ValidationResult.Failure.DoubleFour

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("백돌은 4x4 위치에 돌을 놓을 수 있다")
    fun validateDoubleFourWithWhiteStone() {
        // given
        val beforeDoubleFour = beforeDoubleFour()
        beforeDoubleFour.forEach {
            grid.putStone(Stone(it, StoneColor.WHITE))
        }

        // when
        val acttual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.WHITE),
            )
        val expected = ValidationResult.Success

        // then
        assertThat(acttual).isEqualTo(expected)
    }

    @Test
    @DisplayName("흑돌은 장목 위치에 돌을 놓을 수 없다")
    fun validateOverLineWithBlackStone() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(Stone(it, StoneColor.BLACK))
        }

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.BLACK),
            )
        val expected = ValidationResult.Failure.OverLine

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("백돌은 장목 위치에 돌을 놓을 수 있다")
    fun validateOverLineWithWhiteStone() {
        // given
        val beforeOverLine = beforeOverLine()
        beforeOverLine.forEach {
            grid.putStone(Stone(it, StoneColor.WHITE))
        }

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(getFoulPoint(), StoneColor.WHITE),
            )
        val expected = ValidationResult.Success

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("이미 돌이 있는 위치에 돌을 놓을 수 없다")
    fun validateOccupied() {
        // given
        grid.putStone(Stone(Point(Row(1), Column(2)), StoneColor.BLACK))

        // when
        val actual =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(Point(Row(1), Column(2)), StoneColor.BLACK),
            )
        val actual2 =
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                grid.getStonesByColor(StoneColor.WHITE),
                Stone(Point(Row(1), Column(2)), StoneColor.BLACK),
            )
        val expected = ValidationResult.Failure.Occupied

        // then
        assertThat(actual).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    @DisplayName("돌 다섯개가 연속으로 이어지면 오목이다")
    fun checkWin() {
        // given
        val omokStones = omokPoints()
        omokStones.forEach {
            grid.putStone(Stone(it, StoneColor.BLACK))
        }

        // when
        val actual =
            referee.checkWin(
                RenjuRuleAdapterImpl,
                grid.getStonesByColor(StoneColor.BLACK),
                Stone(
                    POINT_H6,
                    StoneColor.BLACK,
                ),
            )

        // then
        assertThat(actual).isTrue()
    }
}
