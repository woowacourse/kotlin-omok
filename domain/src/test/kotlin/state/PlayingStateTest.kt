package state

import domain.point.Point
import domain.point.Points
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.state.FoulState
import domain.state.PlayerState
import domain.state.PlayingState
import domain.state.WinState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayingStateTest {
    private lateinit var playingState: PlayerState
    private lateinit var rule: OmokRule
    private lateinit var otherPoints: Points

    @BeforeEach
    fun setUp() {
        playingState = PlayingState(Points(Point(1, 1), Point(1, 2)))
        rule = BlackRenjuRule()
        otherPoints = Points()
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
        val playingState = PlayingState(Points(Point(1, 1), Point(1, 2))).add(Point(1, 3), otherPoints, rule)
        val expected = playingState.getAllPoints()
        assertThat(expected).isEqualTo(Points(Point(1, 1), Point(1, 2), Point(1, 3)))
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 반칙을 한 경우 파울 상태를 반환한다`() {
        val playingState = PlayingState(
            Points(
                Point(5, 5),
                Point(6, 6),
                Point(7, 7),
                Point(9, 9),
                Point(10, 10),
            )
        )
        val expected = playingState.add(Point(8, 8), otherPoints, rule)

        assertThat(expected).isInstanceOf(FoulState::class.java)
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 미만이면 동일한 상태를 반환한다`() {
        val playingState = PlayingState(Points(Point(1, 1), Point(1, 2)))
        val expected = playingState.add(Point(1, 3), otherPoints, rule)

        assertThat(expected).isInstanceOf(PlayingState::class.java)
    }

    @Test
    fun `새롭게 오목알을 놓았을 때, 연이어져 있는 오목알이 5개 이상이면 승리 상태를 반환한다`() {
        val playingState = PlayingState(Points(Point(1, 1), Point(1, 2), Point(1, 3), Point(1, 4)))
        val expected = playingState.add(Point(1, 5), otherPoints, rule)

        assertThat(expected).isInstanceOf(WinState::class.java)
    }
}
