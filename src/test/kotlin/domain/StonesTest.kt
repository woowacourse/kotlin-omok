package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

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

    @ParameterizedTest
    @CsvSource(value = ["1,1:2,1:3,1:5,1|4,1", "1,5:2,4:4,2:5,1|3,3", "1,1:2,1:3,1:4,1|5,1", "1,5:1,2:3,3:4,2:4,4:5,1|2,4"], delimiter = '|')
    fun `들을 놓았을 때, 돌이 5개 같은 방향으로 연속되면 해당 색의 돌을 가진 플레이어가 승리한다`(placedStone: String, newStone: String) {
        // given
        val placedStones = placedStone.split(":").map {
            BlackStone(it[0].digitToInt(), it[2].digitToInt())
        }
        val stones = Stones(placedStones)

        // when
        val actual = stones.isWin(BlackStone(newStone[0].digitToInt(), newStone[2].digitToInt()))
        // then
        assertThat(actual).isEqualTo(true)
    }

    fun BlackStone(x: Int, y: Int): Stone {
        return Stone(Color.BLACK, Coordinate.from(x, y)!!)
    }
}
