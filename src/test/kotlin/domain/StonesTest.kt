package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `돌을 놓을때, 해당 위치에 다른 돌이 존재하면 돌을 놓을 수 없다`() {
        // given
        val stone1 = Stone(Color.BLACK, Coordinate.from(3, 3)!!)
        val stone2 = Stone(Color.BLACK, Coordinate.from(3, 3)!!)
        val stones = Stones(listOf(stone1))

        // when
        val message = assertThrows<IllegalArgumentException> {
            stones.place(stone2)
        }.message

        val except = "같은 위치에 이미 돌이 있습니다. 위치 : (3, 3)"

        // then
        assertThat(message).isEqualTo(except)
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
}
