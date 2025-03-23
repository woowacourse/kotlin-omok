package omok.model

import omok.mapper.BlackRuleChecker
import omok.mapper.DoubleFourViolation
import omok.mapper.DoubleThreeViolation
import omok.mapper.OverlineViolation
import omok.mapper.PointMapper
import omok.model.game.Game
import omok.model.rule.BlackOmokRule
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView.Companion.BOARD_SIZE
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.BlackRenjuRule

class BlackOmokRuleTest {
    private val blackRuleChecker =
        BlackRuleChecker(
            rule = BlackRenjuRule(BOARD_SIZE),
            mapper = { pos -> PointMapper().from(pos) },
        )
    private val game = Game(blackRuleChecker)
    private val blackOmokRule = BlackOmokRule(blackRuleChecker)

    @Test
    fun `3-3 반칙을 검증한다`() {
        val positions =
            listOf(
                Position(Row(5), Col(4)),
                Position(Row(5), Col(7)),
                Position(Row(5), Col(6)),
                Position(Row(5), Col(9)),
                Position(Row(6), Col(3)),
                Position(Row(5), Col(8)),
                Position(Row(7), Col(3)),
                Position(Row(4), Col(4)),
            )

        val nextPosition = Position(Row(5), Col(3))

        for (i in positions) {
            game.place(i)
        }

        assertThrows<DoubleThreeViolation> {
            blackOmokRule.validate(
                game.getBoard().stonesMap,
                nextPosition,
                StoneColor.BLACK,
            )
        }
    }

    @Test
    fun `4-4 반칙을 검증한다`() {
        val positions =
            listOf(
                Position(Row(7), Col(5)),
                Position(Row(1), Col(1)),
                Position(Row(7), Col(6)),
                Position(Row(1), Col(2)),
                Position(Row(7), Col(7)),
                Position(Row(1), Col(11)),
                Position(Row(6), Col(4)),
                Position(Row(1), Col(4)),
                Position(Row(5), Col(4)),
                Position(Row(1), Col(10)),
                Position(Row(4), Col(4)),
                Position(Row(2), Col(9)),
            )

        val nextPosition = Position(Row(7), Col(4))

        for (i in positions) {
            game.place(i)
        }

        assertThrows<DoubleFourViolation> {
            blackOmokRule.validate(game.getBoard().stonesMap, nextPosition, StoneColor.BLACK)
        }
    }

    @Test
    fun `4-4 반칙을 검증한다2`() {
        val positions =
            listOf(
                Position(Row(10), Col(9)),
                Position(Row(1), Col(1)),
                Position(Row(11), Col(9)),
                Position(Row(1), Col(2)),
                Position(Row(12), Col(9)),
                Position(Row(1), Col(11)),
                Position(Row(10), Col(8)),
                Position(Row(1), Col(4)),
                Position(Row(11), Col(7)),
                Position(Row(1), Col(10)),
                Position(Row(12), Col(6)),
            )

        val nextPosition = Position(Row(9), Col(9))

        for (i in positions) {
            game.place(i)
        }

        assertThrows<DoubleFourViolation> {
            blackOmokRule.validate(game.getBoard().stonesMap, nextPosition, StoneColor.BLACK)
        }
    }

    @Test
    fun `장목 반칙을 검증한다`() {
        val positions =
            listOf(
                Position(Row(4), Col(3)),
                Position(Row(1), Col(3)),
                Position(Row(4), Col(4)),
                Position(Row(10), Col(11)),
                Position(Row(4), Col(5)),
                Position(Row(1), Col(7)),
                Position(Row(5), Col(5)),
                Position(Row(7), Col(14)),
                Position(Row(4), Col(8)),
                Position(Row(10), Col(10)),
                Position(Row(4), Col(7)),
                Position(Row(5), Col(9)),
            )

        val nextPosition = Position(Row(4), Col(6))

        for (i in positions) {
            game.place(i)
        }

        assertThrows<OverlineViolation> {
            blackOmokRule.validate(game.getBoard().stonesMap, nextPosition, StoneColor.BLACK)
        }
    }
}
