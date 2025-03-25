package omok.view

import omok.domain.Board
import omok.domain.Position
import omok.domain.StoneType

class BoardRenderer(private val size: Int = Board.DEFAULT_SIZE) {
    fun render(board: Board): Array<Array<StoneType>> {
        return Array(size) { row ->
            Array(size) { col ->
                board.getStoneAt(Position(row, col))
            }
        }
    }
}
