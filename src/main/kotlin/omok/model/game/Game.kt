package omok.model.game

import omok.mapper.BlackRuleChecker
import omok.model.board.Board
import omok.model.board.Board.Companion.initBoard
import omok.model.rule.BlackOmokRule
import omok.model.rule.Rule
import omok.model.rule.WhiteOmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Game(
    blackRuleChecker: BlackRuleChecker,
) {
    private var board: Board = initBoard()
    private var turn: StoneColor = StoneColor.BLACK
    private var lastStone: Stone? = null

    private val whiteOmokRule = WhiteOmokRule(BOARD_SIZE)
    private val blackOmokRule = BlackOmokRule(blackRuleChecker)

    fun getBoard(): Board = board

    fun getTurn(): StoneColor = turn

    fun getLastStone(): Stone? = lastStone

    fun place(position: Position): Board {
        validateMove(position)
        applyMove(position)
        return board
    }

    private fun validateMove(position: Position) {
        currentRule().validate(board.stonesMap, position, turn)
    }

    private fun applyMove(position: Position) {
        board = board.positionAt(position, turn)
        lastStone = Stone(position, turn)
        turn = turn.next()
    }

    fun isOmok(): Boolean = lastStone?.let { currentRule().isWin(board.stonesMap, it) } ?: false

    private fun currentRule(): Rule =
        when (turn) {
            StoneColor.BLACK -> blackOmokRule
            StoneColor.WHITE -> whiteOmokRule
        }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
