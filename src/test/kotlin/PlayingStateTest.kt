import domain.state.PlayingState
import domain.state.WinState
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayingStateTest {

    @Test
    fun `오목알을 놓으면 목록에 추가한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE)).add(ONE_THREE_STONE)
        val expected = playingState.hasStone(ONE_THREE_STONE)
        assertThat(expected).isTrue
    }

    @Test
    fun `돌을 놓았을 때 오목알 연이어져 있는 오목알이 5개 미만이면 게임을 계속한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE))
        val expected = playingState.add(ONE_THREE_STONE)

        assertThat(expected).isInstanceOf(PlayingState::class.java)
    }

    @Test
    fun `돌을 놓았을 때 오목알 5개 이상이 연이어져 있으면 승리한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE, ONE_FOUR_STONE))
        val expected = playingState.add(ONE_FIVE_STONE)

        assertThat(expected).isInstanceOf(WinState::class.java)
    }
}
