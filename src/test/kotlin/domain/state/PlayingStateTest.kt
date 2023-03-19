package domain.state

import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayingStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        playingState = PlayingState(Stones(Stone.of(1, 1), Stone.of(1, 2)))
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
        val playingState = PlayingState(Stones(Stone.of(1, 1), Stone.of(1, 2))).add(Stone.of(1, 3), rule)
        val expected = playingState.getAllStones()
        assertThat(expected).isEqualTo(Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3)))
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 미만이면 동일한 상태를 반환한다`() {
        val playingState = PlayingState(Stones(Stone.of(1, 1), Stone.of(1, 2)))
        val expected = playingState.add(Stone.of(1, 3), rule)

        assertThat(expected).isInstanceOf(PlayingState::class.java)
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 이상이면 승리 상태를 반환한다`() {
        val playingState = PlayingState(Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3), Stone.of(1, 4)))
        val expected = playingState.add(Stone.of(1, 5), rule)

        assertThat(expected).isInstanceOf(WinState::class.java)
    }
}
