import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import player.BlackPlayer
import player.Board
import player.Player
import player.Turn
import player.WhitePlayer
import state.PlayingState

class BoardTest {
    @Test
    fun `특정 위치에 돌을 놓으면 참을 반환한다`() {
        val blackPlayer: Player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val whitePlayer: Player = WhitePlayer(PlayingState(Stones(ONE_TWO_STONE)))
        val board = Board(blackPlayer, whitePlayer)
        assertThat(board.putStone(Turn.BLACK, ONE_THREE_STONE)).isTrue
    }

    @Test
    fun `특정 위치에 돌을 놓지 못하면 거짓을 반환한다`() {
        val blackPlayer: Player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val whitePlayer: Player = WhitePlayer(PlayingState(Stones(ONE_TWO_STONE)))
        val board = Board(blackPlayer, whitePlayer)
        assertThat(board.putStone(Turn.BLACK, ONE_TWO_STONE)).isFalse
    }
}
