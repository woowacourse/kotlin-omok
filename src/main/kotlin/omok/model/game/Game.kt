package omok.model.game

import omok.mapper.BlackRuleChecker
import omok.model.board.Board
import omok.model.board.Board.Companion.initBoard
import omok.model.board.BoardDimensions
import omok.model.rule.BlackOmokRule
import omok.model.rule.Rule
import omok.model.rule.WhiteOmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Game(
    blackRuleChecker: BlackRuleChecker,
) {
    private var board: Board = initBoard(BoardDimensions(15, 15))
    private var turn: StoneColor = StoneColor.BLACK
    private var lastStone: Stone? = null

    private val whiteOmokRule = WhiteOmokRule(board.getWidth(), board.getHeight())
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
        currentRule(turn).validate(board, position, turn)
    }

    private fun applyMove(position: Position) {
        board = board.positionAt(position, turn)
        lastStone = Stone(position, turn)
        turn = turn.next()
    }

    private fun currentRule(color: StoneColor): Rule =
        when (color) {
            StoneColor.BLACK -> blackOmokRule
            StoneColor.WHITE -> whiteOmokRule
        }

    fun isOmok(): Boolean =
        lastStone?.let {
            currentRule(it.stoneColor).isWin(board, it)
        } ?: false
}
