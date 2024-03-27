package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.OmokChecker

sealed class GoStone {
    abstract val stoneType: StoneType

    fun putStone(position: Position): GoStone {
        validatePosition(position)
        Board.putStone(position.column, position.row, stoneType)
        Board.changeLstStonePosition(position)
        return if (stoneType == StoneType.BLACK_STONE) WhiteStone else BlackStone
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

    private fun validatePosition(position: Position) {
        require(!Board.checkRenjuRule(position.row, position.column)) { EXCEPTION_FORBIDDEN_MOVE }
        require(
            Board.getStoneType(
                position.column,
                position.row,
            ) == StoneType.NONE,
        ) { EXCEPTION_PLACED_STONE_POSITION }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_MOVE = "금수입니다.\n"
        private const val EXCEPTION_PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"
        private const val BLACK_STONE_VALUE_MESSAGE = "흑"
        private const val WHITE_STONE_VALUE_MESSAGE = "백"
    }
}
