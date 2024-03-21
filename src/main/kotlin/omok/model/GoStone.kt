package omok.model

import omok.model.rule.OmokChecker
import omok.model.rule.OmokRule

abstract class GoStone {
    abstract val stoneType: Stone

    fun putStone(position: Position): Stone {
        require(!OmokRule(Board.board).checkRenjuRule(position.row, position.col)) { "금수입니다.\n" }

        require(Board.board[position.col][position.row] == Stone.NONE) { "이미 놓여진 자리입니다.\n" }
        Board.board[position.col][position.row] = stoneType
        Board.lastPosition = position
        if (stoneType == Stone.BLACK_STONE) {
            return Stone.WHITE_STONE
        }
        return Stone.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean {
        if (Board.board[position.col][position.row] != stoneType) return false

        return OmokChecker.findOmok(position, stoneType)
    }
}
