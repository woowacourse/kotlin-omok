package omok

import omok.fixtures.createPoint
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `좌표에 해당하는 오목판 위치에 돌을 놓을 수 있다`() {
        // given
        val board = Board(mutableMapOf())
        val stone = Stone.BLACK
        val point = createPoint(1, 1)
        val onDecidePoint = { point }
        // when
        board.put(stone, onDecidePoint)
        val actual = board[point]
        // then
        assertThat(actual).isEqualTo(stone)
    }
}

class Board(initialGrid: Map<Point, Stone>) {
    private val _grid = initialGrid.toMutableMap()
    val grid get() = _grid.toMap()

    fun put(stone: Stone, onDecidePoint: () -> (Point), onDone: (Stone, Point) -> Unit = { _, _ -> }) {
        val point = onDecidePoint()
        _grid[point] = stone
    }

    operator fun get(point: Point): Stone? {
        return grid[point]
    }
}
