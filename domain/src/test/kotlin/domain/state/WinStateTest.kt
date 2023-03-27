package domain.state

import domain.point.Point
import domain.point.Points
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WinStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule
    private lateinit var otherPoints: Points

    @BeforeEach
    fun setUp() {
        playingState = WinState(Points())
        rule = BlackRenjuRule()
        otherPoints = Points()
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
        val expected = playingState.add(Point(1, 4), otherPoints, rule)
        val actual = playingState

        assertThat(expected).isEqualTo(actual)
    }
}
