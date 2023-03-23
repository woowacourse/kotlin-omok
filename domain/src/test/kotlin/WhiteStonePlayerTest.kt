import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.WhiteStonePlayer
import domain.stone.Point
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WhiteStonePlayerTest {
    @Test
    fun `흰돌을 사용하는 플레이어는 돌을 놓은 이후에는 다음 플레이어로 BlackStonePlayer를 반환한다`() {
        val player: Player = WhiteStonePlayer()
        val board = Board()

        player.placeStone(board, Point(1, 3))
        val actual = player.toNextPlayer()

        Assertions.assertThat(actual).isInstanceOf(BlackStonePlayer::class.java)
    }
}