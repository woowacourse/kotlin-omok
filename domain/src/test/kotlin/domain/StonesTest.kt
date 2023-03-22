package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `바둑돌을 추가 할 수 있다`() {
        // given
        val stones = makeStones()
        val newStone = Stone(Color.BLACK, Position(5, 6))
        // when
        val actual = stones.addStone(newStone).values
        val expected = makeStones().values + newStone
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은 위치의 바둑돌을 포함하고 있으면 True 이다`() {
        // given
        val stones = makeStones()
        val samePosition = Position(1, 2)
        // when
        val actual = stones.isContainSamePositionStone(samePosition)
        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `같은 위치의 바둑돌을 포함하고 있지 않다면 false 이다`() {
        // given
        val stones = makeStones()
        val differentPosition = Position(7, 9)
        // when
        val actual = stones.isContainSamePositionStone(differentPosition)
        // then
        assertThat(actual).isFalse
    }

    private fun makeStones(): Stones {
        val blackStone = Stone(Color.BLACK, Position(1, 2))
        val whiteStone = Stone(Color.WHITE, Position(2, 3))
        return Stones(listOf(blackStone, whiteStone))
    }
}
