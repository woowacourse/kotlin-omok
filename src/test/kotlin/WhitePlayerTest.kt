import domain.player.Player
import domain.player.WhitePlayer
import domain.point.Points
import domain.state.PlayingState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhitePlayerTest {
    @Test
    fun `특정 위치에 백의 오목알이 없으면 참을 반환한다`() {
        val player: Player = WhitePlayer(PlayingState(Points(ONE_ONE_STONE)))
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 거짓을 반환한다`() {
        val player: Player = WhitePlayer()
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val player: Player = WhitePlayer(PlayingState(Points(ONE_ONE_STONE)))
        assertThat(player.getLastStone()).isEqualTo(ONE_ONE_STONE)
    }
}
