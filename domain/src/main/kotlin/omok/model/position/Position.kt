package omok.model.position

import omok.model.board.Board
import omok.model.result.PutResult
import omok.model.rule.ForbiddenChecker
import omok.model.stone.StoneType

data class Position(val row: Row, val column: Column) {
    fun checkForbidden(): PutResult = ForbiddenChecker.checkForbidden(this)

    fun validatePosition(): PutResult {
        if (Board.board[this.column.value][this.row.value] == StoneType.NONE) return PutResult.Running
        return PutResult.Failure
    }
}
