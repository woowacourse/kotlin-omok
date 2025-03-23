package omok.controller

import omok.model.game.Game
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.InputView
import omok.view.OutputView

class OmokControl(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val game = Game()

    fun run() {
        turn()
    }

    private fun turn() {
        val board = game.getBoard()
        outputView.printBoard(board.stonesMap)
        outputView.printNextTurn(game.getTurn(), game.getLastStone())

        runCatching {
            val input = inputView.inputStone()
            game.place(Position(Row(input.first), Col(input.second)))

            if (game.isOmok()) {
                outputView.printBoard(game.getBoard().stonesMap)
                outputView.printOmok(game.getLastStone())
            } else {
                turn()
            }
        }.getOrElse { exception ->
            outputView.printException(exception.message)
            turn()
        }
    }
}
