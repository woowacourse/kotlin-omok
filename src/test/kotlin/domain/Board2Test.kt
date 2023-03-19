package domain

import domain.domain.* // ktlint-disable no-wildcard-imports
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Board2Test {
    @Test
    fun `보드에 바둑돌을 놓으면,보드에 바둑돌이 추가된다`() {
        // given
        val board = Board2()
        val newStone = Stone(Color.WHITE, Position2(1, 2))
        // when
        board.placeStone(newStone)
        // then
        val actual = board.stones.values
        val expected = listOf<Stone>(newStone)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은 위치에 바둑돌은 놓을 수 없다`() {
        // given
        val stone = Stone(Color.WHITE, Position2(1, 2))
        val board = Board2(stones = Stones(listOf(stone)))
        val samePositionStone = Stone(Color.BLACK, Position2(1, 2))
        // when
        val actual = board.isPossibleToPlace(samePositionStone)
        // then
        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `다른 위치에 바둑돌을 놓을 수 있다`() {
        // given
        val stone = Stone(Color.WHITE, Position2(1, 2))
        val board = Board2(stones = Stones(listOf(stone)))
        val differentPositionStone = Stone(Color.WHITE, Position2(2, 3))
        // when
        val actual = board.isPossibleToPlace(differentPositionStone)
        // then
        Assertions.assertThat(actual).isTrue
    }

    @Test
    fun `보드에 바둑돌이 없다면, 마지막 바둑돌의 위치를 가져오려고 할 때 null을 반환한다`() {
        // given
        val board = Board2()
        // when
        val actual = board.getLastPosition()
        val expected = null
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막으로 둔 바둑돌의 위치를 알 수 있다`() {
        // given
        val stone = Stone(Color.WHITE, Position2(1, 2))
        val board = Board2(stones = Stones(listOf(stone)))
        // when
        val actual = board.getLastPosition()
        val expected = Position2(1, 2)
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
