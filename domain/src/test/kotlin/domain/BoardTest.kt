package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `턴을 흑백 순으로 반복한다`() {
        val stones = Stones()
        val board = Board(stones)
        val coordinates = listOf<Coordinate>(
            Coordinate.from(1, 1),
            Coordinate.from(1, 2),
            Coordinate.from(2, 1),
            Coordinate.from(2, 2),
            Coordinate.from(3, 1),
            Coordinate.from(3, 2),
            Coordinate.from(4, 1),
            Coordinate.from(4, 2),
            Coordinate.from(5, 1),
        )
        var coordinateIndex = 0
        board.repeatTurn {
            coordinates[coordinateIndex++]
        }
        assertAll({
            assertThat(stones.value.filterIndexed { index, _ -> index % 2 == 0 }.all { it.color == Color.BLACK }).isTrue
        }, {
            assertThat(
                stones.value.filterIndexed { index, _ -> index % 2 == 1 }.all {
                    it.color == Color.WHITE
                }
            ).isTrue
        })
    }
}
