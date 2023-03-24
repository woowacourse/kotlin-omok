package game

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.point.Point
import domain.result.TurnResult
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokTest {
    private lateinit var blackRule: OmokRule
    private lateinit var whiteRule: OmokRule

    @BeforeEach
    fun setUp() {
        blackRule = BlackRenjuRule()
        whiteRule = WhiteRenjuRule()
    }

    @Test
    fun `차례마다 플레이어는 돌을 놓을 수 있고 성공 시 Success 객체를 반환한다`() {
        val omok = Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
        val actual = omok.takeTurn(Point(1, 1))
        assertThat(actual is TurnResult.Success).isTrue
    }

    @Test
    fun `차례마다 플레이어는 돌을 놓을 수 있고 실패 시 Failure 객체를 반환한다`() {
        val omok = Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
        omok.takeTurn(Point(1, 1))
        val actual = omok.takeTurn(Point(1, 1))
        assertThat(actual is TurnResult.Failure).isTrue
    }
}
