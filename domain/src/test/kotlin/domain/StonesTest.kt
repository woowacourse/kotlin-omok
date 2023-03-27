package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `다른 위치에 있는 바둑돌을 추가 할 수 있다`() {
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
    fun `같은 위치의 바둑돌을 포함하고 있을 수 없다`() {
        assertThrows<IllegalStateException> { makeDuplicateStones() }
    }

    private fun makeStones(): Stones {
        val blackStone = Stone(Color.BLACK, Position(1, 2))
        val whiteStone = Stone(Color.WHITE, Position(2, 3))
        return Stones(listOf(blackStone, whiteStone))
    }

    private fun makeDuplicateStones(): Stones {
        val blackStone = Stone(Color.BLACK, Position(1, 2))
        val whiteStone = Stone(Color.WHITE, Position(2, 3))
        return Stones(listOf(blackStone, whiteStone))
    }
}
