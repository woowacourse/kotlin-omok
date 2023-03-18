package domain.stone

import ONE_FIVE_STONE
import ONE_FOUR_STONE
import ONE_ONE_STONE
import ONE_THREE_STONE
import ONE_TWO_STONE
import domain.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `각각 다른 위치의 오목알을 가질 수 있다`() {
        assertDoesNotThrow {
            Stones(ONE_ONE_STONE, ONE_TWO_STONE)
        }
    }

    @Test
    fun `중복되는 위치의 오목알을 가지면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Stones(ONE_ONE_STONE, ONE_ONE_STONE)
        }
    }

    @Test
    fun `오목알을 놓을 수 있다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE)
        val newStones = stones.add(ONE_THREE_STONE)

        assertThat(newStones).isEqualTo(Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE))
    }

    @Test
    fun `오목알이 포함되어 있는지 판단한다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE)
        val expected = stones.hasStone(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `오목알이 5개 이상 연이어 있으면 참을 반환한다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE, ONE_FOUR_STONE, ONE_FIVE_STONE)
        val expected = stones.checkWin(ONE_ONE_STONE)

        assertThat(expected).isTrue
    }

    @Test
    fun `오목알이 5개 미만 연이어 있으면 거짓을 반환한다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE)
        val expected = stones.checkWin(ONE_ONE_STONE)

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 오목알을 반환한다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE)
        val expected = stones.lastStone

        assertThat(expected).isEqualTo(ONE_THREE_STONE)
    }

    @Test
    fun `오목알이 놓인 위치 목록을 반환한다`() {
        val stones = Stones(ONE_ONE_STONE, ONE_TWO_STONE, ONE_THREE_STONE)
        val expected = stones.getPositions()
        val actual = listOf(Position(1, 1), Position(1, 2), Position(1, 3))

        assertThat(expected).isEqualTo(actual)
    }
}
