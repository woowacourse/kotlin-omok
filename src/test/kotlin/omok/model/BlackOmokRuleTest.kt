package omok.model

import omok.fixture.A1
import omok.fixture.A10
import omok.fixture.A2
import omok.fixture.C10
import omok.fixture.C11
import omok.fixture.C12
import omok.fixture.C13
import omok.fixture.C14
import omok.fixture.C15
import omok.fixture.D1
import omok.fixture.D12
import omok.fixture.D13
import omok.fixture.D14
import omok.fixture.E12
import omok.fixture.E5
import omok.fixture.F5
import omok.fixture.G5
import omok.fixture.H1
import omok.fixture.H5
import omok.fixture.H6
import omok.fixture.H7
import omok.fixture.H8
import omok.fixture.J1
import omok.fixture.J6
import omok.fixture.K1
import omok.fixture.K15
import omok.fixture.K3
import omok.fixture.K4
import omok.fixture.K5
import omok.fixture.K6
import omok.fixture.K7
import omok.fixture.L1
import omok.fixture.L6
import omok.fixture.M1
import omok.fixture.M6
import omok.fixture.N1
import omok.fixture.O1
import omok.fixture.O10
import omok.mapper.BlackRuleChecker
import omok.mapper.DoubleFourViolation
import omok.mapper.DoubleThreeViolation
import omok.mapper.OverlineViolation
import omok.mapper.PointMapper
import omok.model.game.Game
import omok.model.rule.BlackOmokRule
import omok.model.stone.StoneColor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.BlackRenjuRule

class BlackOmokRuleTest {
    private val blackRuleChecker =
        BlackRuleChecker(
            rule = BlackRenjuRule(),
            mapper = { pos -> PointMapper().from(pos) },
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
            game.placeStone(i)
        }

        assertThrows<DoubleThreeViolation> {
            blackOmokRule.validate(
                game.getBoard(),
                nextPosition,
                StoneColor.BLACK,
            )
        }
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
            game.placeStone(i)
        }

        assertThrows<DoubleFourViolation> {
            blackOmokRule.validate(game.getBoard(), nextPosition, StoneColor.BLACK)
        }
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
            game.placeStone(i)
        }

        assertThrows<DoubleFourViolation> {
            blackOmokRule.validate(game.getBoard(), nextPosition, StoneColor.BLACK)
        }
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
            game.placeStone(i)
        }

        assertThrows<OverlineViolation> {
            blackOmokRule.validate(game.getBoard(), nextPosition, StoneColor.BLACK)
        }
    }
}
