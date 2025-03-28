package omok.model.game

import omok.mapper.BlackRuleChecker
import omok.model.board.Board
import omok.model.board.Board.Companion.initBoard
import omok.model.board.BoardDimensions
import omok.model.rule.BlackOmokRule
import omok.model.rule.OmokRule
import omok.model.rule.WhiteOmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.rule.PlacementError.NoViolation

class Game(
    blackRuleChecker: BlackRuleChecker,
) {
    var board: Board = initBoard(BoardDimensions(15, 15))
        private set
    var turn: StoneColor = StoneColor.BLACK
        private set
    var lastStone: Stone? = null
        private set

    private val whiteOmokRule = WhiteOmokRule(board.getWidth(), board.getHeight())
    private val blackOmokRule = BlackOmokRule(blackRuleChecker)

    fun playTurn(position: Position): PlacementError {
        if (board.hasStoneAt(position)) {
            return PlacementError.AlreadyOccupiedViolation
        }

        val violation = currentRule(turn).validate(board, position, turn)
        if (violation != NoViolation) return violation

        applyPlacement(position)

        return NoViolation
    }

    private fun currentRule(color: StoneColor): OmokRule =
        when (color) {
            StoneColor.BLACK -> blackOmokRule
            StoneColor.WHITE -> whiteOmokRule
        }

    private fun applyPlacement(position: Position) {
        board = board.placeStone(position, turn)
        lastStone = Stone(position, turn)
        turn = turn.next()
    }

    fun isOmok(): Boolean =
        lastStone?.let {
            currentRule(it.stoneColor).isWin(board, it)
        } == true
}
