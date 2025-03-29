package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.fixture.A1
import woowacourse.omok.fixture.A10
import woowacourse.omok.fixture.A2
import woowacourse.omok.fixture.C10
import woowacourse.omok.fixture.C11
import woowacourse.omok.fixture.C12
import woowacourse.omok.fixture.C13
import woowacourse.omok.fixture.C14
import woowacourse.omok.fixture.C15
import woowacourse.omok.fixture.D1
import woowacourse.omok.fixture.D12
import woowacourse.omok.fixture.D13
import woowacourse.omok.fixture.D14
import woowacourse.omok.fixture.E12
import woowacourse.omok.fixture.E5
import woowacourse.omok.fixture.F5
import woowacourse.omok.fixture.G5
import woowacourse.omok.fixture.H1
import woowacourse.omok.fixture.H5
import woowacourse.omok.fixture.H6
import woowacourse.omok.fixture.H7
import woowacourse.omok.fixture.H8
import woowacourse.omok.fixture.J1
import woowacourse.omok.fixture.J6
import woowacourse.omok.fixture.K1
import woowacourse.omok.fixture.K15
import woowacourse.omok.fixture.K3
import woowacourse.omok.fixture.K4
import woowacourse.omok.fixture.K5
import woowacourse.omok.fixture.K6
import woowacourse.omok.fixture.K7
import woowacourse.omok.fixture.L1
import woowacourse.omok.fixture.L6
import woowacourse.omok.fixture.M1
import woowacourse.omok.fixture.M6
import woowacourse.omok.fixture.N1
import woowacourse.omok.fixture.O1
import woowacourse.omok.fixture.O10
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.mapper.PointMapper
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.rule.BlackOmokRule
import woowacourse.omok.model.rule.PlacementError.DoubleFourViolation
import woowacourse.omok.model.rule.PlacementError.DoubleThreeViolation
import woowacourse.omok.model.rule.PlacementError.OverlineViolation
import woowacourse.omok.model.stone.StoneColor

class BlackOmokRuleTest {
    private val blackRuleChecker =
        BlackRuleChecker(
            rule = BlackRenjuRule(),
            mapper = PointMapper { pos -> Point(pos.col.value + 1, pos.row.value + 1) },
        )
    private val game = Game(blackRuleChecker)
    private val blackOmokRule = BlackOmokRule(blackRuleChecker)

    @Test
    fun `3-3 반칙을 검증한다`() {
        val positions =
            listOf(
                E12,
                A1,
                C12,
                D1,
                D13,
                J1,
                D14,
                K1,
            )

        val nextPosition = D12

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(
            blackOmokRule.validate(
                game.board,
                nextPosition,
                StoneColor.BLACK,
            ),
        ).isEqualTo(DoubleThreeViolation)
    }

    @Test
    fun `4-4 반칙을 검증한다`() {
        val positions =
            listOf(
                K4,
                A1,
                K5,
                A10,
                K7,
                M1,
                J6,
                O1,
                L6,
                A2,
                M6,
            )

        val nextPosition = K6

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(blackOmokRule.validate(game.board, nextPosition, StoneColor.BLACK)).isEqualTo(
            DoubleFourViolation,
        )
    }

    @Test
    fun `4-4 반칙을 검증한다2`() {
        val positions =
            listOf(
                G5,
                K1,
                F5,
                N1,
                E5,
                O10,
                H6,
                K3,
                H7,
                K15,
                H8,
                L1,
            )

        val nextPosition = H5

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(blackOmokRule.validate(game.board, nextPosition, StoneColor.BLACK)).isEqualTo(
            DoubleFourViolation,
        )
    }

    @Test
    fun `장목 반칙을 검증한다`() {
        val positions =
            listOf(
                C15,
                K1,
                C14,
                H1,
                C12,
                M1,
                C11,
                O1,
                E12,
                J1,
                C10,
            )

        val nextPosition = C13

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(blackOmokRule.validate(game.board, nextPosition, StoneColor.BLACK)).isEqualTo(
            OverlineViolation,
        )
    }
}
