package domain.state

import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.stone.Stones
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoulStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        playingState = FoulState(Stones())
        rule = RenjuRule()
    }

    @Test
    fun `파울 상태는 반칙을 한 상태이다`() {
        Assertions.assertThat(playingState.isFoul).isTrue
    }

    @Test
    fun `파울 상태는 게임이 종료된 상태이다`() {
        Assertions.assertThat(playingState.isPlaying).isFalse
    }
}
