package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleTest {

    @Test
    fun `놓은 돌이 닫힌 3 닫힌 3 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(2, 11)!!),
            Stone(Color.White, Coordinate.from(0, 0)!!),
            Stone(Color.Black, Coordinate.from(3, 13)!!),
            Stone(Color.White, Coordinate.from(0, 1)!!),
            Stone(Color.Black, Coordinate.from(3, 12)!!),
            Stone(Color.White, Coordinate.from(0, 2)!!),
            Stone(Color.Black, Coordinate.from(4, 11)!!),
            Stone(Color.White, Coordinate.from(0, 3)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isThreeToThree(Stone(Color.Black, Coordinate.from(3, 11)!!))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 열린 4 열린 4 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(1, 5)!!),
            Stone(Color.Black, Coordinate.from(2, 4)!!),
            Stone(Color.Black, Coordinate.from(4, 5)!!),
            Stone(Color.Black, Coordinate.from(4, 4)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isThreeToThree(Stone(Color.Black, Coordinate.from(4, 2)!!))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 열린 4 열린 4 일 때 플레이어는 돌을 놓을 수 없다2`() {
        // given
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(9, 8)!!),
            Stone(Color.Black, Coordinate.from(12, 11)!!),
            Stone(Color.Black, Coordinate.from(12, 9)!!),
            Stone(Color.Black, Coordinate.from(13, 8)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isThreeToThree(Stone(Color.Black, Coordinate.from(11, 10)!!))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `놓은 돌이 닫힌 3 열린 4 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(10, 2)!!),
            Stone(Color.Black, Coordinate.from(10, 5)!!),
            Stone(Color.Black, Coordinate.from(12, 3)!!),
            Stone(Color.Black, Coordinate.from(13, 3)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isThreeToThree(Stone(Color.Black, Coordinate.from(10, 3)!!))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `열린 4가 하나만 있을 때 돌을 놓을 수 있다`() {
        // given
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(10, 2)!!),
            Stone(Color.Black, Coordinate.from(10, 5)!!),
            Stone(Color.Black, Coordinate.from(12, 3)!!),
            Stone(Color.Black, Coordinate.from(13, 3)!!),
            Stone(Color.White, Coordinate.from(9, 3)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isThreeToThree(Stone(Color.Black, Coordinate.from(10, 3)!!))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `흑색돌이 6개 연속 있을 때 육목이다`() {
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(2, 14)!!),
            Stone(Color.Black, Coordinate.from(2, 13)!!),
            Stone(Color.Black, Coordinate.from(2, 11)!!),
            Stone(Color.Black, Coordinate.from(2, 10)!!),
            Stone(Color.Black, Coordinate.from(2, 9)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.findScore(Stone(Color.Black, Coordinate.from(2, 12)!!))

        // then
        assertThat(actual).isGreaterThanOrEqualTo(5)
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다`() {
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(4, 5)!!),
            Stone(Color.Black, Coordinate.from(5, 4)!!),
            Stone(Color.Black, Coordinate.from(6, 3)!!),
            Stone(Color.Black, Coordinate.from(2, 11)!!),
            Stone(Color.Black, Coordinate.from(2, 10)!!),
            Stone(Color.Black, Coordinate.from(2, 9)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isFourToFour(Stone(Color.Black, Coordinate.from(2, 7)!!))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다2`() {
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(2, 12)!!),
            Stone(Color.Black, Coordinate.from(3, 12)!!),
            Stone(Color.Black, Coordinate.from(6, 12)!!),
            Stone(Color.Black, Coordinate.from(8, 12)!!),
            Stone(Color.Black, Coordinate.from(9, 12)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isFourToFour(Stone(Color.Black, Coordinate.from(5, 12)!!))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다3`() {
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(9, 11)!!),
            Stone(Color.Black, Coordinate.from(9, 12)!!),
            Stone(Color.Black, Coordinate.from(9, 8)!!),
            Stone(Color.Black, Coordinate.from(9, 7)!!),
            Stone(Color.Black, Coordinate.from(9, 5)!!),
        )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isFourToFour(Stone(Color.Black, Coordinate.from(9, 9)!!))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다4`() {
        val placedStones = listOf(
            Stone(Color.Black, Coordinate.from(9, 7)!!),
            Stone(Color.Black, Coordinate.from(10, 7)!!),
            Stone(Color.Black, Coordinate.from(7, 7)!!),
            Stone(Color.Black, Coordinate.from(9, 8)!!),
            Stone(Color.Black, Coordinate.from(7, 6)!!),
            Stone(Color.Black, Coordinate.from(5, 4)!!),

            )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isFourToFour(Stone(Color.Black, Coordinate.from(8, 7)!!))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다5`() {
        val placedStones = listOf(
            Stone(Color.White, Coordinate.from(3, 4)!!),
            Stone(Color.Black, Coordinate.from(4, 4)!!),
            Stone(Color.Black, Coordinate.from(5, 4)!!),
            Stone(Color.Black, Coordinate.from(6, 4)!!),
            Stone(Color.White, Coordinate.from(7, 8)!!),
            Stone(Color.Black, Coordinate.from(7, 7)!!),
            Stone(Color.Black, Coordinate.from(7, 6)!!),
            Stone(Color.Black, Coordinate.from(7, 5)!!),

            )
        val stones = Stones(placedStones)
        val renjuRule = RenjuRule(stones)

        // when
        val actual = renjuRule.isFourToFour(Stone(Color.Black, Coordinate.from(7, 4)!!))

        assertThat(actual).isTrue
    }
}
