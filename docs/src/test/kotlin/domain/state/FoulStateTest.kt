package domain.state

import domain.point.Points
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoulStateTest {
    private lateinit var playingState: PlayerState

    @BeforeEach
    fun setUp() {
        playingState = FoulState(Points())
    }

    @Test
    fun `파울 상태는 반칙을 한 상태이다`() {
        assertThat(playingState.isFoul).isTrue
    }

    @Test
    fun `파울 상태는 게임이 종료된 상태이다`() {
        assertThat(playingState.isPlaying).isFalse
    }
}
