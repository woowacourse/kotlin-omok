package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker
import omok.model.rule.RenjuRule

abstract class GoStone {
    abstract val stoneType: Stone

    fun putStone(position: Position): Stone {
        validatePosition(position)
        Board.board[position.col.value][position.row.value] = stoneType
        Board.lastPosition = position
        return if (stoneType == Stone.BLACK_STONE) Stone.WHITE_STONE else Stone.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean = OmokChecker.findOmok(position, stoneType)

    private fun validatePosition(position: Position) {
        require(!RenjuRule(Board.board).checkRenjuRule(position)) { EXCEPTION_FORBIDDEN_MOVE }
        require(Board.board[position.col.value][position.row.value] == Stone.NONE) { EXCEPTION_PLACED_STONE_POSITION }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_MOVE = "금수입니다.\n"
        private const val EXCEPTION_PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"
    }
}
