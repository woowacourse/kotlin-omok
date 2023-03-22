import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.WhiteStonePlayer
import domain.stone.Color
import domain.stone.Point
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackStonePlayerTest {

    @Test
    fun `흑돌을 사용하는 플레이어가 33에 해당하는 위치에 돌을 놓을 수 있는지 확인한다면 false를 return한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(3, 12, Color.Black),
            Stone(4, 13, Color.Black),
            Stone(4, 14, Color.Black),
            Stone(5, 12, Color.Black),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            Point(4, 12)
        )

        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `흑돌을 사용하는 플레이어가 44에 해당하는 위치에 돌을 놓을 수 있는지 확인한다면 false를 return한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(6, 5, Color.Black),
            Stone(8, 6, Color.Black),
            Stone(8, 7, Color.Black),
            Stone(8, 8, Color.Black),
            Stone(10, 8, Color.Black),
            Stone(10, 9, Color.Black),
            Stone(11, 8, Color.Black),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            Point(9, 8)
        )

        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `흑돌을 사용하는 플레이어가 규칙에 위반되지 않은 곳에 돌을 놓으려고 한다면 true를 반환한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(6, 5, Color.Black),
            Stone(8, 6, Color.Black),
            Stone(8, 7, Color.Black),
            Stone(8, 8, Color.Black),
            Stone(10, 8, Color.Black),
            Stone(10, 9, Color.Black),
            Stone(11, 8, Color.Black),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            Point(2, 1)
        )

        Assertions.assertThat(actual).isTrue
    }

    @Test
    fun `흑돌을 사용하는 플레이어는 돌을 놓은 이후에는 다음 플레이어로 WhiteStonePlayer를 반환한다`() {
        val player: Player = BlackStonePlayer()
        val board = Board()

        player.placeStone(board, Point(1, 3))
        val actual = player.toNextPlayer()

        assertThat(actual).isInstanceOf(WhiteStonePlayer::class.java)
    }
}
