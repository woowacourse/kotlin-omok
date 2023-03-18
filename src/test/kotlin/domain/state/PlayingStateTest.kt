package domain.state

import ONE_FIVE_STONE
import ONE_FOUR_STONE
import ONE_ONE_STONE
import ONE_THREE_STONE
import ONE_TWO_STONE
import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayingStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE))
        rule = RenjuRule()
    }

    @Test
    fun `플레잉 상태는 게임을 진행 중이다`() {
        assertThat(playingState.isPlaying).isTrue
    }

    @Test
    fun `플레잉 상태는 파울링이 아닌 상태이다`() {
        assertThat(playingState.isFoul).isFalse
    }

    @Test
    fun `오목알을 놓으면 목록에 추가한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE)).add(ONE_THREE_STONE, rule)
        val expected = playingState.getAllStones()
        assertThat(expected).isEqualTo(Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE))
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 미만이면 동일한 상태를 반환한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE))
        val expected = playingState.add(ONE_THREE_STONE, rule)

        assertThat(expected).isInstanceOf(PlayingState::class.java)
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 이상이면 승리 상태를 반환한다`() {
        val playingState = PlayingState(Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE, ONE_FOUR_STONE))
        val expected = playingState.add(ONE_FIVE_STONE, rule)

        assertThat(expected).isInstanceOf(WinState::class.java)
    }
}
