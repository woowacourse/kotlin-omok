package player

import domain.player.Player
import domain.player.WhitePlayer
import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import domain.state.PlayingState
import domain.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhitePlayerTest {
    private lateinit var otherStones: Points
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        otherStones = Points(
            Point(5, 5),
            Point(6, 6),
            Point(7, 7),
            Point(9, 9),
            Point(10, 10),
        )
        rule = WhiteRenjuRule()
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 참을 반환한다`() {
        val player: Player = WhitePlayer(PlayingState(Points(Point(1, 1))), rule)
        val expected = player.isPlaced(Point(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 거짓을 반환한다`() {
        val player: Player = WhitePlayer(rule = rule)
        val expected = player.isPlaced(Point(1, 1))

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val player: Player = WhitePlayer(Point(1, 1))
        assertThat(player.getLastStone()).isEqualTo(Point(1, 1))
    }

    @Test
    fun `벡돌 플레이어는 돌의 색상이 흰색이다`() {
        val player: Player = WhitePlayer(rule = rule)
        assertThat(player.getStoneColor()).isEqualTo(StoneColor.WHITE)
    }

    private fun WhitePlayer(point: Point) = WhitePlayer(PlayingState(Points(point)), rule)
}
