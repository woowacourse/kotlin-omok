package player

import domain.player.BlackPlayer
import domain.point.Point
import domain.point.Points
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.state.PlayingState

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackPlayerTest {
    private lateinit var otherStones: Points
    private lateinit var blackRule: OmokRule

    @BeforeEach
    fun setUp() {
        otherStones = Points(Point(5, 5), Point(6, 6), Point(7, 7), Point(9, 9), Point(10, 10))
        blackRule = BlackRenjuRule()
    }

    @Test
    fun `특정 위치에 흑의 오목알이 있으면 참을 반환한다`() {
        val player = BlackPlayer(Point(1, 1))
        val expected = player.isPlaced(Point(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 흑의 오목알이 없으면 거짓을 반환한다`() {
        val player = BlackPlayer(Point(1, 1))
        val expected = player.isPlaced(Point(3, 3))

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val player = BlackPlayer(Point(1, 1))
        assertThat(player.getLastStone()).isEqualTo(Point(1, 1))
    }

    @Test
    fun `새롭게 오목알을 두었을 때, 룰을 어기지 않았다면 파울이 아니다`() {
        val player = BlackPlayer(Point(1, 1))
        val expected = player.putStone(Point(1, 2), otherStones).isFoul

        assertThat(expected).isFalse
    }

    @Test
    fun `새롭게 오목알을 두었을 때, 룰을 어기면 파울이다`() {
        val player = BlackPlayer(Point(1, 1), Point(1, 3), Point(1, 4), Point(1, 5), Point(1, 6))
        val expected = player.putStone(Point(1, 2), otherStones).isFoul

        assertThat(expected).isTrue
    }

    @Test
    fun `플레이어의 상태가 게임이 끝나지 않은 상태라면 참을 반환한다`() {
        val player = BlackPlayer(Point(1, 1), Point(1, 3), Point(1, 4), Point(1, 5), Point(1, 6))
        val expected = player.isPlaying

        assertThat(expected).isTrue
    }

    private fun BlackPlayer(vararg points: Point) =
        BlackPlayer(PlayingState(Points(points.toList())), blackRule)
}
