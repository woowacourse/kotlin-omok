import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import player.BlackPlayer
import player.Player
import state.PlayingState

class BlackPlayerTest {
    @Test
    fun `특정 위치에 흑의 오목알이 없으면 참을 반환한다`() {
        val player: Player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 흑의 오목알이 없으면 거짓을 반환한다`() {
        val player: Player = BlackPlayer()
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val player: Player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        assertThat(player.getLastStone()).isEqualTo(ONE_ONE_STONE)
    }
}
