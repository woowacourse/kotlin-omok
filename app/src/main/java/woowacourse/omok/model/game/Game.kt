package woowacourse.omok.model.game

import omok.model.stone.position.Position
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Board.Companion.initBoard
import woowacourse.omok.model.board.BoardDimensions
import woowacourse.omok.model.rule.BlackOmokRule
import woowacourse.omok.model.rule.OmokRule
import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.rule.WhiteOmokRule
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class Game(
    blackRuleChecker: BlackRuleChecker,
) {
    var board: Board = initBoard(BoardDimensions(15, 15))
        private set
    var turn: StoneColor = StoneColor.BLACK
        private set
    var lastStone: Stone? = null
        private set

    private val whiteOmokRule = WhiteOmokRule(board.dimensions)
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

    fun applyPlacement(position: Position) {
        board = board.placeStone(position, turn)
        lastStone = Stone(position, turn)
        turn = turn.next()
    }

    fun isOmok(): Boolean =
        lastStone?.let {
            currentRule(it.stoneColor).isWin(board, it)
        } == true
}
