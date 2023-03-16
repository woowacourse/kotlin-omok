package domain

import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(Board())
        OmokView.printStart()

        while (true) {
            val position = OmokView.putPhase(omokGame.board, omokGame.turn)
            if (omokGame.checkWinner(position)) break
            val success = omokGame.putStone(position)

            if (!success) OmokView.printError() else omokGame.changeTurn()
        }
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
