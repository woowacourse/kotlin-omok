package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재하면 돌을 놓을 수 없다`() {
        // given
        val stone1 = Stone(Color.BLACK, Coordinate.from(3, 3))
        val stone2 = Stone(Color.BLACK, Coordinate.from(3, 3))
        val stones = Stones(listOf(stone1))
        // when
        val actual = stones.place(stone2)

        // then
        assertThat(actual).isEqualTo(PlaceResult.ERROR_ALREADY_PLACE)
    }

    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재지 않으면 돌을 놓을 수 있다`() {
        // given
        val stone1 = Stone(Color.BLACK, Coordinate.from(3, 3))
        val stone2 = Stone(Color.BLACK, Coordinate.from(4, 3))
        val stones = Stones(listOf(stone1))

        // when
        stones.place(stone2)

        // then
        assertThat(stones.value.last()).isEqualTo(stone2)
    }

    @Test
    fun `흑돌일 때 33 렌주룰이 적용된다`() {
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
        val actual = stones.validateRenju(BlackStone(3, 11))

        // then
        assertThat(actual).isEqualTo(PlaceResult.ERROR_RENJU_RULE)
    }

    @Test
    fun `백돌일 때 33 렌주룰이 적용되지 않는다`() {
        // given
        val placedStones = listOf<Stone>(
            WhiteStone(2, 11),
            BlackStone(0, 0),
            WhiteStone(3, 13),
            BlackStone(0, 1),
            WhiteStone(3, 12),
            BlackStone(0, 2),
            WhiteStone(4, 11),
            BlackStone(0, 3),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.validateRenju(WhiteStone(3, 11))

        // then
        assertThat(actual).isEqualTo(PlaceResult.SUCCESS)
    }

    @Test
    fun `흑돌일 때 44 렌주룰이 적용된다`() {
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
        val actual = stones.validateRenju(BlackStone(2, 7))

        assertThat(actual).isEqualTo(PlaceResult.ERROR_RENJU_RULE)
    }

    @Test
    fun `백돌일 때 44 렌주룰이 적용되지 않는다`() {
        val placedStones = listOf<Stone>(
            WhiteStone(4, 5),
            WhiteStone(5, 4),
            WhiteStone(6, 3),
            WhiteStone(2, 11),
            WhiteStone(2, 10),
            WhiteStone(2, 9),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.validateRenju(WhiteStone(2, 7))

        assertThat(actual).isEqualTo(PlaceResult.SUCCESS)
    }

    @Test
    fun `흑색돌은 육목이 적용된다`() {
        val placedStones = listOf<Stone>(
            BlackStone(2, 14),
            BlackStone(2, 13),
            BlackStone(2, 11),
            BlackStone(2, 10),
            BlackStone(2, 9),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.validateRenju(BlackStone(2, 12))

        // then
        assertThat(actual).isEqualTo(PlaceResult.ERROR_RENJU_RULE)
    }

    @Test
    fun `백돌은 육목이 적용되지 않는다`() {
        val placedStones = listOf<Stone>(
            WhiteStone(2, 14),
            WhiteStone(2, 13),
            WhiteStone(2, 11),
            WhiteStone(2, 10),
            WhiteStone(2, 9),
        )
        val stones = Stones(placedStones)

        // when
        val actual = stones.validateRenju(WhiteStone(2, 12))

        // then
        assertThat(actual).isEqualTo(PlaceResult.SUCCESS)
    }

    @Test
    fun `연속한 돌이 5개 놓여졌을 때 이긴다`() {
        //given
        val placedStones = listOf<Stone>(
            WhiteStone(2, 14),
            WhiteStone(2, 13),
            WhiteStone(2, 12),
            WhiteStone(2, 11),
            WhiteStone(2, 10),
        )
        val stones = Stones(placedStones)

        //when
        val actual = stones.isWinPlace()

        //then
        assertThat(actual).isTrue
    }

    @Test
    fun `돌이 없을 때, 승리 판별시, IllegalArugumentException을 반환한다`() {
        //given
        val placedStones = emptyList<Stone>()
        val stones = Stones(placedStones)

        //then
        assertThrows<java.lang.IllegalArgumentException> {
            stones.isWinPlace()
        }

    }
}
