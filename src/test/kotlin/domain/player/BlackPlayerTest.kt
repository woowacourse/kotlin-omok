package domain.player

import ONE_FIVE_STONE
import ONE_FOUR_STONE
import ONE_ONE_STONE
import ONE_SIX_STONE
import ONE_THREE_STONE
import ONE_TWO_STONE
import domain.position.Position
import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackPlayerTest {
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
    fun `특정 위치에 흑의 오목알이 없으면 참을 반환한다`() {
        val player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `특정 위치에 흑의 오목알이 없으면 거짓을 반환한다`() {
        val player = BlackPlayer()
        val expected = player.isPlaced(ONE_ONE_STONE)

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        assertThat(player.getLastStone()).isEqualTo(ONE_ONE_STONE)
    }

    @Test
    fun `새롭게 오목알을 두었을 때, 룰을 어기지 않았다면 파울이 아니다`() {
        val player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE)))
        val expected = player.putStone(ONE_TWO_STONE, otherStones, rule).isFoul()

        assertThat(expected).isFalse
    }

    @Test
    fun `새롭게 오목알을 두었을 때, 룰을 어기면 파울이다`() {
        val player = BlackPlayer(
            PlayingState(
                Stones(
                    ONE_ONE_STONE,
                    ONE_THREE_STONE,
                    ONE_FOUR_STONE,
                    ONE_FIVE_STONE,
                    ONE_SIX_STONE
                )
            )
        )
        val expected = player.putStone(ONE_TWO_STONE, otherStones, rule).isFoul()

        assertThat(expected).isTrue
    }

    @Test
    fun `플레이어가 아직 게임을 진행 중이라면 돌을 놓을 수 있다`() {
        val player = BlackPlayer(
            PlayingState(
                Stones(
                    ONE_ONE_STONE,
                    ONE_THREE_STONE,
                    ONE_FOUR_STONE,
                    ONE_FIVE_STONE,
                    ONE_SIX_STONE
                )
            )
        )
        val expected = player.canPlace()

        assertThat(expected).isTrue
    }

    @Test
    fun `플레이어가 놓은 오목알의 위치를 반환한다`() {
        val player = BlackPlayer(PlayingState(Stones(ONE_ONE_STONE, ONE_THREE_STONE, ONE_FOUR_STONE)))
        val expected = player.getPositions()

        assertThat(expected).isEqualTo(listOf(Position(1, 1), Position(1, 3), Position(1, 4)))
    }
}
