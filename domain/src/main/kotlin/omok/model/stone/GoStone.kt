package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.result.PutResultUtils.isOccupiedOrForbidden
import omok.model.result.PutResultUtils.isRunning
import omok.model.rule.OmokChecker

abstract class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): PutResult {
        var putResult = position.validatePosition()
        if (isOccupiedOrForbidden(putResult)) return putResult

        putResult = position.checkForbidden()
        if (isOccupiedOrForbidden(putResult)) return putResult

        if (isRunning(putResult)) {
            Board.board[position.column.value][position.row.value] = stoneType
            Board.updateLastPosition(position)
        }
        return putResult
    }

    fun findOmok(position: Position): Boolean = OmokChecker.findOmok(position, stoneType)

    companion object {
        fun GoStone.change() = if (this.stoneType == StoneType.BLACK_STONE) WhiteStone() else BlackStone()
    }
}
