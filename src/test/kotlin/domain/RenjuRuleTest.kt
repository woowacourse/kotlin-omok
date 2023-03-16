package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleTest {

    @Test
    fun `놓은 돌이 닫힌 3 닫힌 3 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(2, 11),
            BlackStone(3, 13),
            BlackStone(3, 12),
            BlackStone(4, 11),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.renjuRule.threeToThree(BlackStone(3, 11))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 열린 4 열린 4 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(1, 5),
            BlackStone(2, 4),
            BlackStone(4, 5),
            BlackStone(4, 4),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.renjuRule.threeToThree(BlackStone(4, 2))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 열린 4 열린 4 일 때 플레이어는 돌을 놓을 수 없다2`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(9, 8),
            BlackStone(12, 11),
            BlackStone(12, 9),
            BlackStone(13, 8),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.renjuRule.threeToThree(BlackStone(11, 10))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 닫힌 3 열린 4 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(10, 2),
            BlackStone(10, 5),
            BlackStone(12, 3),
            BlackStone(13, 3),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.renjuRule.threeToThree(BlackStone(10, 3))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `열린 4가 하나만 있을 때 돌을 놓을 수 있다`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(10, 2),
            BlackStone(10, 5),
            BlackStone(12, 3),
            BlackStone(13, 3),
            WhiteStone(9, 3),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.renjuRule.threeToThree(BlackStone(10, 3))

        // then
        assertThat(actual).isFalse
    }

    fun BlackStone(x: Int, y: Int): Stone {
        return Stone(Color.BLACK, Coordinate.from(x, y)!!)
    }

    fun WhiteStone(x: Int, y: Int): Stone {
        return Stone(Color.WHITE, Coordinate.from(x, y)!!)
    }
}
