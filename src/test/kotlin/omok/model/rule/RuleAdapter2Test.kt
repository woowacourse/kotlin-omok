package omok.model.rule

import omok.model.Board
import omok.model.ContinualStonesCondition
import omok.model.Position
import omok.model.Stone
import omok.model.StonePosition
import omok.model.initBoard
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule2
import omok.model.rule.library.ThreeThreeRule
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RuleAdapter2Test {
    private lateinit var board: Board

    private val renjuRule =
        RuleAdapter2(
            ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
            ForbiddenRules(
                ThreeThreeRule.forBlack(),
                FourFourRule.forBlack(),
                OverlineRule2(),
            ),
        )

    /*
    3-3 규칙 테스트: docs/3-3 금수 테스트 케이스.png 참조
     */
    @Test
    fun `유효한 위치인지 판단한다(3-3 규칙 A 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(1, 3), Stone.BLACK),
                StonePosition(Position(2, 3), Stone.BLACK),
                StonePosition(Position(3, 2), Stone.BLACK),
                StonePosition(Position(3, 4), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(3, 3))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(3-3 규칙 B 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(9, 1), Stone.BLACK),
                StonePosition(Position(10, 2), Stone.BLACK),
                StonePosition(Position(9, 4), Stone.BLACK),
                StonePosition(Position(10, 4), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(12, 4))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(3-3 규칙 C 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(3, 12), Stone.BLACK),
                StonePosition(Position(5, 12), Stone.BLACK),
                StonePosition(Position(6, 9), Stone.BLACK),
                StonePosition(Position(6, 13), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(4, 11))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(3-3 규칙 D 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(5, 5), Stone.BLACK),
                StonePosition(Position(8, 5), Stone.BLACK),
                StonePosition(Position(7, 7), Stone.BLACK),
                StonePosition(Position(7, 8), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(7, 5))

        // then
        assertThat(actual).isFalse
    }

    /*
    4-4 규칙 테스트: docs/4-4 금수 테스트 케이스.png 참조
     */
    @Test
    fun `유효한 위치인지 판단한다(4-4 규칙 A 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(3, 2), Stone.BLACK),
                StonePosition(Position(3, 3), Stone.BLACK),
                StonePosition(Position(3, 6), Stone.BLACK),
                StonePosition(Position(3, 8), Stone.BLACK),
                StonePosition(Position(3, 9), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(3, 5))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(4-4 규칙 B 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(3, 9), Stone.BLACK),
                StonePosition(Position(6, 9), Stone.BLACK),
                StonePosition(Position(7, 9), Stone.BLACK),
                StonePosition(Position(9, 9), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(5, 9))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(4-4 규칙 C 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(1, 2), Stone.BLACK),
                StonePosition(Position(3, 2), Stone.BLACK),
                StonePosition(Position(4, 2), Stone.BLACK),
                StonePosition(Position(5, 2), Stone.BLACK),
                StonePosition(Position(9, 4), Stone.BLACK),
                StonePosition(Position(10, 5), Stone.BLACK),
                StonePosition(Position(11, 6), Stone.BLACK),
            )

        // when
        val actual = renjuRule.validPosition(board, Position(7, 2))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `유효한 위치인지 판단한다(4-4 규칙 D 위반)`() {
        // given
        board =
            initBoard(
                StonePosition(Position(5, 9), Stone.BLACK),
                StonePosition(Position(6, 9), Stone.BLACK),
                StonePosition(Position(6, 10), Stone.BLACK),
                StonePosition(Position(6, 7), Stone.BLACK),
                StonePosition(Position(7, 7), Stone.BLACK),
                StonePosition(Position(9, 5), Stone.BLACK),
            )
        // when
        val actual = renjuRule.validPosition(board, Position(6, 8))

        // then
        assertThat(actual).isFalse
    }

    /*
    장목 금수 규칙 테스트
     */
    @Test
    fun `승리 조건이 정확히 오목일 때, 돌을 두려는 위치로 육목 이상이 되면 놓을 수 없다`() {
        // given
        val ruleAdapter =
            RuleAdapter2(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenRules(OverlineRule2()),
            )

        val board =
            initBoard(
                StonePosition(Position(0, 0), Stone.WHITE),
                StonePosition(Position(0, 7), Stone.WHITE),
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
                StonePosition(Position(0, 6), Stone.BLACK),
            )

        val actual = ruleAdapter.validPosition(board, Position(0, 3))
        assertThat(actual).isFalse
    }

    @Test
    fun `승리 조건이 정확히 사목일 때, 돌을 두려는 위치로 오목 이상이 되면 놓을 수 없다`() {
        // given
        val ruleAdapter =
            RuleAdapter2(
                ContinualStonesWinningCondition(ContinualStonesStandard(4), ContinualStonesCondition.EXACT),
                ForbiddenRules(OverlineRule2()),
            )

        val board =
            initBoard(
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
            )

        // when
        val actual = ruleAdapter.validPosition(board, Position(0, 3))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `사목으로 지정할 경우 더블 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            RuleAdapter2(
                ContinualStonesWinningCondition(ContinualStonesStandard(4), ContinualStonesCondition.EXACT),
                ForbiddenRules(
                    ThreeThreeRule.forWhite(),
                    FourFourRule.forWhite(),
                ),
            )
        }
    }

    @Test
    fun `우승 조건이 정확히 N 목이 아닌, N 목 이상일 경우 장목 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            RuleAdapter2(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.CAN_OVERLINE),
                ForbiddenRules(
                    OverlineRule2(),
                ),
            )
        }
    }
}
