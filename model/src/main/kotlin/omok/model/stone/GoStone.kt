package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.rule.OmokChecker

sealed class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): PutResult {
        val putResult = Board.putStone(position.column, position.row, stoneType)
        if (putResult == PutResult.Running) {
            Board.changeLastStonePosition(position)
        }
        return putResult
    }

    fun findOmok(position: Position): Boolean =
        if (
            Board.getStoneType(position.column, position.row) != stoneType
        ) {
            false
        } else {
            OmokChecker.findOmok(position, stoneType)
        }

    fun GoStone.value() =
        when (this) {
            BlackStone -> BLACK_STONE_VALUE_MESSAGE
            WhiteStone -> WHITE_STONE_VALUE_MESSAGE
        }

    fun GoStone.changeStone() =
        when (this) {
            BlackStone -> WhiteStone
            WhiteStone -> BlackStone
        }

    companion object {
        private const val BLACK_STONE_VALUE_MESSAGE = "흑"
        private const val WHITE_STONE_VALUE_MESSAGE = "백"
    }
}
