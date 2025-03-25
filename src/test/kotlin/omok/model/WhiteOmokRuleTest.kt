package omok.model

import omok.fixture.A1
import omok.fixture.A11
import omok.fixture.A2
import omok.fixture.A3
import omok.fixture.A4
import omok.fixture.A5
import omok.fixture.B1
import omok.fixture.B2
import omok.fixture.C1
import omok.fixture.C3
import omok.fixture.D1
import omok.fixture.D3
import omok.fixture.D4
import omok.fixture.E1
import omok.fixture.E3
import omok.fixture.E5
import omok.fixture.F3
import omok.fixture.G11
import omok.fixture.G3
import omok.fixture.H1
import omok.fixture.H10
import omok.fixture.H3
import omok.fixture.J1
import omok.fixture.K1
import omok.fixture.K10
import omok.fixture.K11
import omok.fixture.K15
import omok.fixture.L15
import omok.fixture.M1
import omok.fixture.M11
import omok.fixture.N11
import omok.fixture.O1
import omok.mapper.BlackRuleChecker
import omok.mapper.PointMapper
import omok.model.game.Game
import omok.model.rule.WhiteOmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule

class WhiteOmokRuleTest {
    private val game =
        Game(
            blackRuleChecker =
                BlackRuleChecker(
                    BlackRenjuRule(),
                    mapper = { pos -> PointMapper().from(pos) },
                ),
        )

    private val whiteOmokRule = WhiteOmokRule(game.getBoard().getWidth(), game.getBoard().getHeight())

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
            game.placeStone(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard(), it) }).isTrue()
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
            game.placeStone(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard(), it) }).isTrue()
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
            game.placeStone(i)
        }
        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard(), it) }).isTrue()
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
            game.placeStone(i)
        }

        assertThat(game.getLastStone()?.let { whiteOmokRule.isWin(game.getBoard(), it) }).isTrue()
    }
}
