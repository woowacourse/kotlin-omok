package player

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.point.Points
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.state.FoulState
import domain.state.PlayingState
import domain.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayersTest {
    private lateinit var blackRenjuRule: BlackRenjuRule
    private lateinit var whiteRenjuRule: WhiteRenjuRule

    @BeforeEach
    fun setUp() {
        blackRenjuRule = BlackRenjuRule()
        whiteRenjuRule = WhiteRenjuRule()
    }

    @Test
    fun `첫 턴은 흑이며, 현재 턴의 플레이어가 오목알을 성공적으로 놓으면 턴이 백으로 바뀐다`() {
        val players =
            Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.putPoint(Point(1, 1))
        val expected = Players(
            BlackPlayer(rule = blackRenjuRule, Point(1, 1)),
            WhitePlayer(rule = whiteRenjuRule)
        )

        assertThat(actual.curPlayerColor).isEqualTo(expected.curPlayerColor)
    }

    @Test
    fun `현재 턴이 백일 때, 현재 턴의 플레이어가 오목알을 성공적으로 놓으면 턴이 흑으로 바뀐다`() {
        val players = Players(
            BlackPlayer(rule = blackRenjuRule, Point(2, 1)),
            WhitePlayer(rule = whiteRenjuRule)
        )
        val actual = players.putPoint(Point(1, 1))
        val expected = Players(
            BlackPlayer(rule = blackRenjuRule, Point(2, 1)),
            WhitePlayer(rule = whiteRenjuRule, Point(1, 1))
        )

        assertThat(actual.curPlayerColor).isEqualTo(expected.curPlayerColor)
    }

    @Test
    fun `모든 플레이어들의 상태가 플레이 중이면 참을 반환한다`() {
        val players =
            Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.isPlaying
        assertThat(actual).isTrue
    }

    @Test
    fun `한 명이라도 플레이어의 상태가 플레이 중이 아니면 거짓을 반환한다`() {
        val players = Players(
            BlackPlayer(state = FoulState(), rule = blackRenjuRule),
            WhitePlayer(rule = whiteRenjuRule)
        )
        val actual = players.isPlaying
        assertThat(actual).isFalse
    }

    @Test
    fun `현재 턴의 플레이어의 돌 색깔을 반환한다`() {
        val players =
            Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.curPlayerColor
        assertThat(actual).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val players = Players(
            BlackPlayer(rule = blackRenjuRule),
            WhitePlayer(rule = whiteRenjuRule)
        ).putPoint(Point(1, 1)).putPoint(Point(2, 2))
        val actual = players.getLastPoint()

        assertThat(actual).isEqualTo(Point(2, 2))
    }

    @Test
    fun `플레이어 목록을 반환한다`() {
        val players =
            Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.toList()

        assertThat(actual).usingRecursiveComparison().isEqualTo(
            listOf(
                BlackPlayer(rule = blackRenjuRule),
                WhitePlayer(rule = whiteRenjuRule)
            )
        )
    }

    @Test
    fun `자기 자신을 깊은 복사할 수 있다`() {
        val players =
            Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.copy()
        assertThat(actual).isNotSameAs(players)
    }

    private fun Players(blackPlayer: BlackPlayer, whitePlayer: WhitePlayer) = Players(blackPlayer, listOf(blackPlayer, whitePlayer))
    private fun BlackPlayer(rule: BlackRenjuRule, vararg points: Point) =
        BlackPlayer(PlayingState(Points(points.toList())), rule)
    private fun WhitePlayer(rule: WhiteRenjuRule, vararg points: Point) = WhitePlayer(PlayingState(Points(points.toList())), rule)
}
