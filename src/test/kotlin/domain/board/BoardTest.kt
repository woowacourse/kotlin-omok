package domain.board

import domain.player.BlackPlayer
import domain.player.Player
import domain.player.Players
import domain.player.WhitePlayer
import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.state.FoulState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        rule = RenjuRule()
    }

    @Test
    fun `특정 위치에 돌을 놓으면, 돌을 놓은 보드를 반환한다`() {
        val blackPlayer = BlackPlayer(PlayingState(Stones(Stone.of(1, 1))))
        val whitePlayer = WhitePlayer(PlayingState(Stones(Stone.of(1, 2))))
        val board = Board(blackPlayer, whitePlayer, rule)

        val expected = board.putStone(StoneColor.BLACK, Stone.of(1, 3))

        assertThat(expected).isInstanceOf(Board::class.java)
    }

    @Test
    fun `특정 위치에 돌을 놓지 못하면, null을 반환한다`() {
        val blackPlayer = BlackPlayer(PlayingState(Stones(Stone.of(1, 1))))
        val whitePlayer = WhitePlayer(PlayingState(Stones(Stone.of(1, 2))))
        val board = Board(blackPlayer, whitePlayer, rule)

        val expected = board.putStone(StoneColor.BLACK, Stone.of(1, 2))

        assertThat(expected).isNull()
    }

    @Test
    fun `플레이어들이 모두 게임을 진행 중이면 참을 반환한다`() {
        val blackPlayer = BlackPlayer(PlayingState(Stones(Stone.of(1, 1))))
        val whitePlayer = WhitePlayer(PlayingState(Stones(Stone.of(1, 2))))
        val board = Board(blackPlayer, whitePlayer, rule)

        val expected = board.isRunning

        assertThat(expected).isTrue
    }

    @Test
    fun `플레이어 중에 파울을 한 사람이 있는지 확인한다`() {
        val blackPlayer: Player = BlackPlayer(FoulState(Stones(Stone.of(1, 1))))
        val whitePlayer: Player = WhitePlayer(PlayingState(Stones(Stone.of(1, 2))))
        val board = Board(blackPlayer, whitePlayer, rule)

        val expected = board.isFouling

        assertThat(expected).isTrue
    }

    @Test
    fun `플레이어 목록을 반환한다`() {
        val blackPlayer = BlackPlayer(PlayingState(Stones()))
        val whitePlayer = WhitePlayer(PlayingState(Stones()))
        val board = Board(blackPlayer, whitePlayer, rule)

        val expected = board.getPlayers()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(Players(blackPlayer, whitePlayer, rule))
    }
}
