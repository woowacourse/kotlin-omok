package omok.model

abstract class GoStone {
    abstract val stoneType: Stone

    fun putStone(position: Position): Stone {
        require(!OmokRule(Board.board).checkRule(position.row, position.col)) { "금수입니다." }

        require(Board.board[position.col][position.row] == Stone.NONE) { "이미 놓여진 자리입니다." }
        Board.board[position.col][position.row] = stoneType
        Board.lastPosition = position
        if (stoneType == Stone.BLACK_STONE) {
            return Stone.WHITE_STONE
        }
        return Stone.BLACK_STONE
    }

    fun findOmok(position: Position): Boolean {
        if (Board.board[position.col][position.row] != stoneType) return false

        return Board.findOmok(position, stoneType)
    }
}
