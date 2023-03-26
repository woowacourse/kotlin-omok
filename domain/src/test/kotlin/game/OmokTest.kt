package game

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.point.Point
import domain.point.Points
import domain.result.TurnResult
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import domain.state.PlayingState
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
    fun `차례마다 플레이어는 돌을 놓을 수 있고 계속 게임이 진행중인 경우 Playing 객체를 반환한다`() {
        val omok = Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
        val actual = omok.takeTurn(Point(1, 1))
        assertThat(actual is TurnResult.Playing && !actual.isExistPoint).isTrue
    }

    @Test
    fun `돌을 놓았는데 이미 돌이 존재하는 위치라면 Playing 객체에 isExistPoint에 1을 담아 반환한다`() {
        val omok = Omok(BlackPlayer(Point(1, 1), rule = blackRule), WhitePlayer(rule = whiteRule))
        val actual = omok.takeTurn(Point(1, 1))
        assertThat(actual is TurnResult.Playing && actual.isExistPoint).isTrue
    }

    @Test
    fun `돌을 놓고 이긴 경우 Win 객체를 반환한다`() {
        val omok = Omok(BlackPlayer(Point(1, 1), Point(1, 3), Point(1, 4), Point(1, 5), rule = blackRule),
            WhitePlayer(Point(10, 1), Point(10, 3), Point(10, 4), Point(10, 5), rule = whiteRule))
        val actual = omok.takeTurn(Point(1, 2))
        assertThat(actual is TurnResult.Win).isTrue
    }

    @Test
    fun `돌을 놓고 반칙한 경우 Foul 객체를 반환한다`() {
        val omok = Omok(BlackPlayer(Point(1, 1), Point(1, 3), Point(1, 4), Point(1, 5), Point(1, 6), rule = blackRule),
            WhitePlayer(Point(10, 1), Point(10, 3), Point(10, 4), Point(10, 5), Point(10, 6), rule = whiteRule))
        val actual = omok.takeTurn(Point(1, 2))
        assertThat(actual is TurnResult.Foul).isTrue
    }

    private fun BlackPlayer(vararg points: Point, rule: OmokRule) = BlackPlayer(PlayingState(Points(points.toList())), rule)
    private fun WhitePlayer(vararg points: Point, rule: OmokRule) = WhitePlayer(PlayingState(Points(points.toList())), rule)
}
