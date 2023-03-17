package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재하면 돌을 놓을 수 없다`() {
        // given
        val stone1 = Stone(Color.BLACK, Coordinate.from(3, 3)!!)
        val stone2 = Stone(Color.BLACK, Coordinate.from(3, 3)!!)
        val stones = Stones(listOf(stone1))

        // when
        val actual = stones.validateDuplicatedCoordinate(stone2)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재지 않으면 돌을 놓을 수 있다`() {
        // given
        val stone1 = Stone(Color.BLACK, Coordinate.from(3, 3)!!)
        val stone2 = Stone(Color.BLACK, Coordinate.from(4, 3)!!)
        val stones = Stones(listOf(stone1))

        // when
        stones.place(stone2)

        // then
        assertThat(stones.value.last()).isEqualTo(stone2)
    }

    @Test
    fun `돌을 놓으면 입력받은 위치에 돌 놓인다`() {
        // given
        val stones = Stones()

        // when
        val stone = Stone(Color.BLACK, Coordinate.from(1, 2)!!)
        stones.place(stone)

        // then
        val except = Stone(Color.BLACK, Coordinate.from(1, 2)!!)
        assertThat(stones.value.last()).isEqualTo(except)
    }
}
