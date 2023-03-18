import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.stone.Color
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlackStonePlayerTest {

    @Test
    fun `흑돌을 사용하는 플레이어가 33에 해당하는 위치에 돌을 놓을 수 있는지 확인한다면 false를 return한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.BLACK),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.BLACK),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            createPoint(4, 12)
        )

        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `흑돌을 사용하는 플레이어가 44에 해당하는 위치에 돌을 놓을 수 있는지 확인한다면 false를 return한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            createPoint(9, 8)
        )

        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `흑돌을 사용하는 플레이어가 규칙에 위반되지 않은 곳에 돌을 놓으려고 한다면 true를 반환한다`() {
        val player: Player = BlackStonePlayer()
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )
        val actual = player.isPossibleToPlace(
            Board(stones),
            createPoint(2, 1)
        )

        Assertions.assertThat(actual).isTrue
    }
}
