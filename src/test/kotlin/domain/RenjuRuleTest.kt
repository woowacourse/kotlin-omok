package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleTest {

    @Test
    fun `놓은 돌이 닫힌 3 닫힌 3 일 때 플레이어는 돌을 놓을 수 없다`() {
        // given
        val placedStones = listOf<Stone>(
            BlackStone(2, 11),
            WhiteStone(0, 0),
            BlackStone(3, 13),
            WhiteStone(0, 1),
            BlackStone(3, 12),
            WhiteStone(0, 2),
            BlackStone(4, 11),
            WhiteStone(0, 3),
        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isThreeToThree(stones, BlackStone(3, 11))

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
        val actual = RenjuRule.isThreeToThree(stones, BlackStone(4, 2))

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
        val actual = RenjuRule.isThreeToThree(stones, BlackStone(11, 10))

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
        val actual = RenjuRule.isThreeToThree(stones, BlackStone(10, 3))

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
        val actual = RenjuRule.isThreeToThree(stones, BlackStone(10, 3))

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `흑색돌이 6개 연속 있을 때 육목이다`() {
        val placedStones = listOf<Stone>(
            BlackStone(2, 14),
            BlackStone(2, 13),
            BlackStone(2, 11),
            BlackStone(2, 10),
            BlackStone(2, 9),
        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.findScore(stones, BlackStone(2, 12))

        // then
        assertThat(actual).isGreaterThanOrEqualTo(5)
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다`() {
        val placedStones = listOf<Stone>(
            BlackStone(4, 5),
            BlackStone(5, 4),
            BlackStone(6, 3),
            BlackStone(2, 11),
            BlackStone(2, 10),
            BlackStone(2, 9),
        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isFourToFour(stones, BlackStone(2, 7))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다2`() {
        val placedStones = listOf<Stone>(
            BlackStone(2, 12),
            BlackStone(3, 12),
            BlackStone(6, 12),
            BlackStone(8, 12),
            BlackStone(9, 12),
        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isFourToFour(stones, BlackStone(5, 12))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다3`() {
        val placedStones = listOf<Stone>(
            BlackStone(9, 11),
            BlackStone(9, 12),
            BlackStone(9, 8),
            BlackStone(9, 7),
            BlackStone(9, 5),
        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isFourToFour(stones, BlackStone(9, 9))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다4`() {
        val placedStones = listOf<Stone>(
            BlackStone(9, 7),
            BlackStone(10, 7),
            BlackStone(7, 7),
            BlackStone(9, 8),
            BlackStone(7, 6),
            BlackStone(5, 4),

        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isFourToFour(stones, BlackStone(8, 7))

        assertThat(actual).isTrue
    }

    @Test
    fun `흑색 돌이 4 4일 떄 돌을 놓을 수 없다5`() {
        val placedStones = listOf<Stone>(
            WhiteStone(3, 4),
            BlackStone(4, 4),
            BlackStone(5, 4),
            BlackStone(6, 4),
            WhiteStone(7, 8),
            BlackStone(7, 7),
            BlackStone(7, 6),
            BlackStone(7, 5),

        )
        val stones = Stones(placedStones)

        // when
        val actual = RenjuRule.isFourToFour(stones, BlackStone(7, 4))

        assertThat(actual).isTrue
    }

    fun BlackStone(x: Int, y: Int): Stone {
        return Stone(Color.BLACK, Coordinate.from(x, y)!!)
    }

    fun WhiteStone(x: Int, y: Int): Stone {
        return Stone(Color.WHITE, Coordinate.from(x, y)!!)
    }
}
