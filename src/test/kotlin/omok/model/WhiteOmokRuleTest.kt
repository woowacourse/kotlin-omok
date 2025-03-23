package omok.model

import omok.mapper.BlackRuleChecker
import omok.mapper.PointMapper
import omok.model.game.Game
import omok.model.rule.WhiteOmokRule
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView.Companion.BOARD_SIZE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule

class WhiteOmokRuleTest {
    private val game =
        Game(
            blackRuleChecker =
                BlackRuleChecker(
                    BlackRenjuRule(BOARD_SIZE),
                    mapper = { pos -> PointMapper().from(pos) },
                ),
        )

    private val whiteOmokRule = WhiteOmokRule(BOARD_SIZE)

    @Test
    fun `대각선으로 오목임을 확인할 수 있다`() {
        val whiteOmokRule = WhiteOmokRule(BOARD_SIZE)

        val position1 = Position(Row(2), Col(1)) // Black
        val position2 = Position(Row(1), Col(1)) // White
        val position3 = Position(Row(1), Col(10)) // Black
        val position4 = Position(Row(2), Col(2)) // White
        val position5 = Position(Row(7), Col(7)) // Black
        val position6 = Position(Row(3), Col(3)) // White
        val position7 = Position(Row(5), Col(8)) // Black
        val position8 = Position(Row(4), Col(4)) // White
        val position9 = Position(Row(1), Col(7)) // Black
        val position10 = Position(Row(5), Col(5)) // White

        val positions =
            listOf(
                position1,
                position2,
                position3,
                position4,
                position5,
                position6,
                position7,
                position8,
                position9,
                position10,
            )

        for (i in positions) {
            game.place(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard().stonesMap, it) }).isTrue()
    }

    @Test
    fun `세로로 오목임을 확인할 수 있다`() {
        val position1 = Position(Row(2), Col(1)) // Black
        val position2 = Position(Row(1), Col(1)) // White
        val position3 = Position(Row(1), Col(10)) // Black
        val position4 = Position(Row(1), Col(2)) // White
        val position5 = Position(Row(7), Col(7)) // Black
        val position6 = Position(Row(1), Col(3)) // White
        val position7 = Position(Row(5), Col(8)) // Black
        val position8 = Position(Row(1), Col(4)) // White
        val position9 = Position(Row(1), Col(7)) // Black
        val position10 = Position(Row(1), Col(5)) // White

        val positions =
            listOf(
                position1,
                position2,
                position3,
                position4,
                position5,
                position6,
                position7,
                position8,
                position9,
                position10,
            )

        for (i in positions) {
            game.place(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard().stonesMap, it) }).isTrue()
    }

    @Test
    fun `가로로 오목임을 확인할 수 있다`() {
        val position1 = Position(Row(10), Col(1)) // Black
        val position2 = Position(Row(1), Col(1)) // White
        val position3 = Position(Row(1), Col(10)) // Black
        val position4 = Position(Row(2), Col(1)) // White
        val position5 = Position(Row(7), Col(7)) // Black
        val position6 = Position(Row(3), Col(1)) // White
        val position7 = Position(Row(5), Col(8)) // Black
        val position8 = Position(Row(4), Col(1)) // White
        val position9 = Position(Row(1), Col(7)) // Black
        val position10 = Position(Row(5), Col(1)) // White

        val positions =
            listOf(
                position1,
                position2,
                position3,
                position4,
                position5,
                position6,
                position7,
                position8,
                position9,
                position10,
            )

        for (i in positions) {
            game.place(i)
        }
        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard().stonesMap, it) }).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val position1 = Position(Row(2), Col(1)) // Black
        val position2 = Position(Row(1), Col(1)) // White
        val position3 = Position(Row(1), Col(10)) // Black
        val position4 = Position(Row(1), Col(2)) // White
        val position5 = Position(Row(7), Col(7)) // Black
        val position6 = Position(Row(1), Col(6)) // White
        val position7 = Position(Row(5), Col(8)) // Black
        val position8 = Position(Row(1), Col(4)) // White
        val position9 = Position(Row(1), Col(7)) // Black
        val position10 = Position(Row(1), Col(5)) // White
        val position11 = Position(Row(10), Col(7)) // Black
        val position12 = Position(Row(1), Col(3)) // White

        val positions =
            listOf(
                position1,
                position2,
                position3,
                position4,
                position5,
                position6,
                position7,
                position8,
                position9,
                position10,
                position11,
                position12,
            )

        for (i in positions) {
            game.place(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard().stonesMap, it) }).isTrue()
    }
}
