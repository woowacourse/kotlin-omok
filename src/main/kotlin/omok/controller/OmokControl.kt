package omok.controller

import omok.mapper.BlackRuleChecker
import omok.mapper.NoViolation
import omok.mapper.PointMapper
import omok.mapper.ViolationType
import omok.model.game.Game
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.InputView
import omok.view.OutputView
import rule.BlackRenjuRule

class OmokControl(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val blackRuleChecker =
        BlackRuleChecker(
            rule = BlackRenjuRule(),
            mapper = { position -> PointMapper().from(position) },
        )
    private val game = Game(blackRuleChecker)

    fun run() {
        turn()
    }

    private fun turn(showBoard: Boolean = true) {
        if (showBoard) printCurrentState()

        val input = inputView.inputStone(game.getBoard())
        val rowValue = input.first
        val colValue = input.second

        val violation = game.placeStone(Position(Row(rowValue), Col(colValue)))

        if (violation != NoViolation) {
            handleViolation(violation)
            return
        }

        if (game.isOmok()) {
            outputView.printBoard(game.getBoard())
            outputView.printOmok(game.getLastStone())
        } else {
            turn()
        }
    }

    private fun printCurrentState() {
        outputView.printBoard(game.getBoard())
        outputView.printNextTurn(game.getTurn(), game.getLastStone())
    }

    private fun handleViolation(violation: ViolationType) {
        outputView.printException(violation.message)
        turn(showBoard = false)
    }
}
