package omok.model.game

import omok.external.mapper.toPoint
import omok.model.board.Board
import omok.model.board.Board.Companion.initBoard
import omok.model.rule.OmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import rule.BlackRenjuRule
import rule.type.Violation

class Game {
    private var board: Board = initBoard()
    private var turn: StoneColor = StoneColor.BLACK
    private var lastStone: Stone? = null

    private val rule = OmokRule(BOARD_SIZE)
    private val foulRule = BlackRenjuRule(BOARD_SIZE, BOARD_SIZE)

    fun getBoard(): Board = board

    fun getTurn(): StoneColor = turn

    fun getLastStone(): Stone? = lastStone

    fun place(position: Position): Board {
        if (turn == StoneColor.BLACK) {
            val blackPoints = board.stonesMap.filter { it.value == StoneColor.BLACK }.map { it.key.toPoint() }
            val whitePoints = board.stonesMap.filter { it.value == StoneColor.WHITE }.map { it.key.toPoint() }

            val violation = foulRule.checkAnyFoulCondition(blackPoints, whitePoints, position.toPoint())
            when (violation) {
                Violation.DOUBLE_THREE -> throw Exception(ERROR_DOUBLE_THREE)
                Violation.DOUBLE_FOUR -> throw Exception(ERROR_DOUBLE_FOUR)
                Violation.OVERLINE -> throw Exception(ERROR_OVERLINE)
                Violation.NONE -> {}
            }
        }

        board = board.positionAt(position, turn)
        lastStone = Stone(position, turn)
        turn = turn.next()

        return board
    }

    fun isOmok(): Boolean =
        lastStone?.let {
            rule.isLastStoneOmok(board.stonesMap, it)
        } ?: false

    companion object {
        private const val BOARD_SIZE = 15

        private const val ERROR_DOUBLE_THREE = "3-3 반칙이 발생했습니다"
        private const val ERROR_DOUBLE_FOUR = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVERLINE = "장목 반칙이 발생했습니다"
    }
}
