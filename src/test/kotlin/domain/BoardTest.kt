package domain

import error.ConsoleErrorHandler
import error.CoordinateResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BoardTest {
    @Test
    fun `턴을 흑백 순으로 반복한다`() {
        val players = Players()
        val stones = Stones()
        val omokRule = RenjuRule(stones)
        val coordinates = listOf(
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
        val board = Board(players, stones)
        var coordinateIndex = 0
        board.repeatTurn({
            (coordinates[coordinateIndex++] as CoordinateResult.Success).coordinate
        }, omokRule, ConsoleErrorHandler)
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
