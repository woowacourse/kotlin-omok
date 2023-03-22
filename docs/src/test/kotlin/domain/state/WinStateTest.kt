package domain.state

import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WinStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        playingState = WinState(Stones())
        rule = RenjuRule()
    }

    @Test
    fun `승리 상태는 게임이 종료된 상태이다`() {
        assertThat(playingState.isPlaying).isFalse
    }

    @Test
    fun `승리 상태는 반칙을 하지 않은 상태이다`() {
        assertThat(playingState.isFoul).isFalse
    }

    @Test
    fun `게임이 종료된 상태에서 오목알을 새롭게 추가하지 못한다`() {
        val expected = playingState.add(Stone.of(1, 4), rule)
        val actual = playingState

        assertThat(expected).isEqualTo(actual)
    }
}
