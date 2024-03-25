package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker

abstract class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): StoneType {
        validatePosition(position)
        Board.board[position.column.value][position.row.value] = stoneType
        Board.updateLastPosition(position)
        return if (stoneType == StoneType.BLACK_STONE) StoneType.WHITE_STONE else StoneType.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean = OmokChecker.findOmok(position, stoneType)

    private fun validatePosition(position: Position) {
        require(!position.checkForbidden()) { EXCEPTION_FORBIDDEN_MOVE }
        require(Board.board[position.column.value][position.row.value] == StoneType.NONE) { EXCEPTION_PLACED_STONE_POSITION }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_MOVE = "금수입니다.\n"
        private const val EXCEPTION_PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"
    }
}
