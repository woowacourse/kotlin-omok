package woowacourse.omok.domain.model.rule

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition
import woowacourse.omok.model.initBoard

class RuleAdapterTest {
    private lateinit var board: Board

    private val renjuRule =
        RuleAdapter(
            OverlineRule.forBlack(
                ContinualStonesWinningCondition(
                    ContinualStonesStandard(5),
                    ContinualStonesCondition.STRICT,
                ),
            ),
            ForbiddenRules(
                ThreeThreeRule.forBlack(),
                FourFourRule.forBlack(),
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
        val actual = renjuRule.violated(board, Position(3, 3))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(12, 4))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(4, 11))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(7, 5))

        // then
        Assertions.assertThat(actual).isTrue()
    }

    @Test
    fun `유효한 위치인지 판단한다(3-3 규칙 D 위반)2`() {
        // given
        board =
            initBoard(
                StonePosition(Position(5, 5), Stone.BLACK),
                StonePosition(Position(8, 5), Stone.BLACK),
                StonePosition(Position(7, 7), Stone.BLACK),
                StonePosition(Position(7, 8), Stone.BLACK),
            )

        // when
        val actual = renjuRule.violated(board, Position(7, 5))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(3, 5))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(5, 9))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(7, 2))

        // then
        Assertions.assertThat(actual).isTrue()
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
        val actual = renjuRule.violated(board, Position(6, 8))

        // then
        Assertions.assertThat(actual).isTrue()
    }

    /*
    장목 금수 규칙 테스트
     */
    @Test
    fun `승리 조건이 정확히 오목일 때, 돌을 두려는 위치로 육목 이상이 되면 놓을 수 없다`() {
        // given
        val ruleAdapter =
            RuleAdapter(
                OverlineRule.forBlack(
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(5),
                        ContinualStonesCondition.STRICT,
                    ),
                ),
                ForbiddenRules(),
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

        val actual = ruleAdapter.violated(board, Position(0, 3))
        Assertions.assertThat(actual).isTrue()
    }

    @Test
    fun `승리 조건이 정확히 사목일 때, 돌을 두려는 위치로 오목 이상이 되면 놓을 수 없다`() {
        // given
        val rules =
            RuleAdapter(
                OverlineRule.forBlack(
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(4),
                        ContinualStonesCondition.STRICT,
                    ),
                ),
                ForbiddenRules(),
            )

        val board =
            initBoard(
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
            )
        // when
        val actual = rules.violated(board, Position(0, 3))

        // then
        Assertions.assertThat(actual).isTrue()
    }

    @Test
    fun `사목으로 지정할 경우 더블 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            RuleAdapter(
                OverlineRule.forBlack(
                    ContinualStonesWinningCondition(
                        ContinualStonesStandard(4),
                        ContinualStonesCondition.STRICT,
                    ),
                ),
                ForbiddenRules(
                    FourFourRule.forBlack(),
                ),
            )
        }
    }
}
