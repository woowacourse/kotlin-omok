package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker

abstract class GoStone {
    abstract val stoneType: Stone

    fun putStone(position: Position): Stone {
        validatePosition(position)
        Board.board[position.column.value][position.row.value] = stoneType
        Board.lastPosition = position
        return if (stoneType == Stone.BLACK_STONE) Stone.WHITE_STONE else Stone.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean = OmokChecker.findOmok(position, stoneType)

    private fun validatePosition(position: Position) {
        require(!position.checkForbidden()) { EXCEPTION_FORBIDDEN_MOVE }
        require(Board.board[position.column.value][position.row.value] == Stone.NONE) { EXCEPTION_PLACED_STONE_POSITION }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_MOVE = "금수입니다.\n"
        private const val EXCEPTION_PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"
    }
}
