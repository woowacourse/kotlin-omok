package domain

import error.OmokResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재하면 돌을 놓을 수 없다`() {
        // given
        val stone1 = Stone(Color.Black, (Coordinate.from(3, 3) as OmokResult.Success<Coordinate>).value)
        val stone2 = Stone(Color.Black, (Coordinate.from(3, 3) as OmokResult.Success<Coordinate>).value)
        val stones = Stones(listOf(stone1))

        // when
        val actual = stones.validateDuplicatedCoordinate(stone2)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재지 않으면 돌을 놓을 수 있다`() {
        // given
        val stone1 = Stone(Color.Black, (Coordinate.from(3, 3) as OmokResult.Success<Coordinate>).value)
        val stone2 = Stone(Color.Black, (Coordinate.from(4, 3) as OmokResult.Success<Coordinate>).value)
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
        val stone = Stone(Color.Black, (Coordinate.from(1, 2) as OmokResult.Success<Coordinate>).value)
        stones.place(stone)

        // then
        val except = Stone(Color.Black, (Coordinate.from(1, 2) as OmokResult.Success<Coordinate>).value)
        assertThat(stones.value.last()).isEqualTo(except)
    }
}
