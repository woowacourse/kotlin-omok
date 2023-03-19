package domain.player

import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhitePlayerTest {
    private lateinit var otherStones: Stones
    private lateinit var rule: OmokRule

    @BeforeEach
    fun setUp() {
        otherStones = Stones(
            Stone.of(5, 5),
            Stone.of(6, 6),
            Stone.of(7, 7),
            Stone.of(9, 9),
            Stone.of(10, 10),
        )
        rule = RenjuRule()
    }

    @Test
    fun `특정 위치에 새롭게 오목알을 놓는다`() {
        val player = WhitePlayer(PlayingState(Stones()))

        val expected = player.putStone(Stone.of(1, 1), otherStones, rule).getAllStones()
        val actual = Stones(Stone.of(1, 1))

        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 참을 반환한다`() {
        val player = WhitePlayer(PlayingState(Stones(Stone.of(1, 1))))
        val expected = player.isPlaced(Stone.of(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 백의 오목알이 없으면 거짓을 반환한다`() {
        val player = WhitePlayer()
        val expected = player.isPlaced(Stone.of(1, 1))

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 오목알을 반환한다`() {
        val player = WhitePlayer(PlayingState(Stones(Stone.of(1, 1))))

        assertThat(player.getLastStone()).isEqualTo(Stone.of(1, 1))
    }
}
