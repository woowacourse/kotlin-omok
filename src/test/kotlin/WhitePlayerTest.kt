import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import player.Player
import player.WhitePlayer
import state.PlayingState

class WhitePlayerTest {
    @Test
    fun `특정 위치에 백의 오목알이 없으면 참을 반환한다`() {
        val player: Player = WhitePlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 거짓을 반환한다`() {
        val player: Player = WhitePlayer()
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isFalse
    }
}
