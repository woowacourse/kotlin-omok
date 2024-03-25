package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker
import omok.model.rule.OmokRule

abstract class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): StoneType {
        validatePosition(position)
        Board.board[position.col][position.row] = stoneType
        Board.lastPosition = position
        return if (stoneType == StoneType.BLACK_STONE) StoneType.WHITE_STONE else StoneType.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean {
        if (Board.board[position.col][position.row] != stoneType) return false

        return OmokChecker.findOmok(position, stoneType)
    }

    private fun validatePosition(position: Position) {
        require(!OmokRule(Board.board).checkRenjuRule(position.row, position.col)) { EXCEPTION_FORBIDDEN_MOVE }
        require(Board.board[position.col][position.row] == StoneType.NONE) { EXCEPTION_PLACED_STONE_POSITION }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_MOVE = "금수입니다.\n"
        private const val EXCEPTION_PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"
    }
}
