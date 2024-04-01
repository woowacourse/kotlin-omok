package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker

abstract class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): StoneType {
        Board.board[position.column.value][position.row.value] = stoneType
        Board.updateLastPosition(position)
        return stoneType
    }

    fun findOmok(position: Position): Boolean = OmokChecker.findOmok(position, stoneType)
}
