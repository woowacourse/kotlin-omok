package omok.controller

import omok.mapper.BlackRuleChecker
import omok.mapper.PointMapper
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

        try {
            val input = inputView.inputStone(game.getBoard())
            game.placeStone(Position(Row(input.first), Col(input.second)))

            if (game.isOmok()) {
                outputView.printBoard(game.getBoard())
                outputView.printOmok(game.getLastStone())
            } else {
                turn()
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun printCurrentState() {
        outputView.printBoard(game.getBoard())
        outputView.printNextTurn(game.getTurn(), game.getLastStone())
    }

    private fun handleException(e: Exception) {
        outputView.printException(e.message)
        turn(showBoard = false)
    }
}
