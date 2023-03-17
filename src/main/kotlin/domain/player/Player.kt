package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class Player(val color: Color) {

    fun putStone(currentBoard: Board, getPosition: (latestStone: Stone?) -> Position): Board? {
        val position = getPosition(currentBoard.latestStone)

        if (currentBoard.isPlaced(position)) {
            return null
        }
        return Board(currentBoard.placedStones + Stone(position, color))
    }
}
