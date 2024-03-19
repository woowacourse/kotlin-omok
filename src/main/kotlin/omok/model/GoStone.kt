package omok.model

abstract class GoStone {
    abstract val stoneType: Stone
    private lateinit var lastPosition: Position

    fun getLastPosition(): Position? {
        if (::lastPosition.isInitialized) return lastPosition
        return null
    }

    fun putStone(position: Position) {
        require(Board.board[position.row][position.col] == Stone.NONE) { "이미 놓여진 자리입니다." }
        Board.board[position.row][position.col] = stoneType
        lastPosition = position
    }

    fun findOmok(): Boolean {
        if (Board.board[lastPosition.row][lastPosition.col] != stoneType) return false

        return Board.findOmok(lastPosition, stoneType)
    }
}
