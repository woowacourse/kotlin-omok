package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.fixture.A1
import woowacourse.omok.fixture.A11
import woowacourse.omok.fixture.A2
import woowacourse.omok.fixture.A3
import woowacourse.omok.fixture.A4
import woowacourse.omok.fixture.A5
import woowacourse.omok.fixture.B1
import woowacourse.omok.fixture.B2
import woowacourse.omok.fixture.C1
import woowacourse.omok.fixture.C3
import woowacourse.omok.fixture.D1
import woowacourse.omok.fixture.D3
import woowacourse.omok.fixture.D4
import woowacourse.omok.fixture.E1
import woowacourse.omok.fixture.E3
import woowacourse.omok.fixture.E5
import woowacourse.omok.fixture.F3
import woowacourse.omok.fixture.G11
import woowacourse.omok.fixture.G3
import woowacourse.omok.fixture.H1
import woowacourse.omok.fixture.H10
import woowacourse.omok.fixture.H3
import woowacourse.omok.fixture.J1
import woowacourse.omok.fixture.K1
import woowacourse.omok.fixture.K10
import woowacourse.omok.fixture.K11
import woowacourse.omok.fixture.K15
import woowacourse.omok.fixture.L15
import woowacourse.omok.fixture.M1
import woowacourse.omok.fixture.M11
import woowacourse.omok.fixture.N11
import woowacourse.omok.fixture.O1
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.mapper.PointMapper
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.rule.WhiteOmokRule

class WhiteOmokRuleTest {
    private val game =
        Game(
            blackRuleChecker =
                BlackRuleChecker(
                    BlackRenjuRule(),
                    mapper = PointMapper { pos -> Point(pos.col.value + 1, pos.row.value + 1) },
                ),
        )

    private val dimensions = game.board.dimensions
    private val whiteOmokRule = WhiteOmokRule(dimensions)

    @Test
    fun `흰돌이 대각선으로 오목임을 확인할 수 있다`() {
        val positions =
            listOf(
                M1,
                A1,
                K1,
                B2,
                M11,
                C3,
                O1,
                D4,
                J1,
                E5,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(game.lastStone?.let { whiteOmokRule.isWin(game.board, it) }).isTrue()
    }

    @Test
    fun `흰돌이 세로로 오목임을 확인할 수 있다`() {
        val positions =
            listOf(
                K1,
                A1,
                M1,
                A2,
                K11,
                A3,
                O1,
                A4,
                J1,
                A5,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(game.lastStone?.let { whiteOmokRule.isWin(game.board, it) }).isTrue()
    }

    @Test
    fun `흰돌이 가로로 오목임을 확인할 수 있다`() {
        val positions =
            listOf(
                K10,
                A1,
                M1,
                B1,
                H10,
                C1,
                G11,
                D1,
                O1,
                E1,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }
        assertThat(game.lastStone?.let { whiteOmokRule.isWin(game.board, it) }).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val positions =
            listOf(
                A1,
                C3,
                K15,
                D3,
                H1,
                E3,
                N11,
                F3,
                A11,
                H3,
                L15,
                G3,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(game.lastStone?.let { whiteOmokRule.isWin(game.board, it) }).isTrue()
    }
}
