package omok.controller

import omok.model.board.OmokBoard
import omok.model.player.BlackPlayer
import omok.model.player.Player
import omok.model.player.state.BlackPlayerState
import omok.view.OmokView

class OmokController(
    private val omokView: OmokView,
) {
    fun run() {
        omokView.printStartMessage()
        val omokBoard = OmokBoard()
        val currentPlayer: Player = BlackPlayer(BlackPlayerState())
        playGame(currentPlayer, omokBoard)
    }

    private fun playGame(
        player: Player,
        omokBoard: OmokBoard,
    ) {
        var currentPlayer = player
        while (true) {
            playerTurn(currentPlayer, omokBoard)
            if (finishGame(currentPlayer, omokBoard)) break
            omokView.printOmokBoard(omokBoard.board)
            currentPlayer = currentPlayer.nextPlayer()
        }
    }

    private inline fun handleTurnException(inputStone: () -> Unit) {
        runCatching { inputStone() }
            .onFailure { error ->
                println(error.message)
                inputStone()
            }
    }

    private fun playerTurn(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ) {
        handleTurnException {
            val position = omokView.inputPosition(currentPlayer)
            currentPlayer.put(position, omokBoard)
        }
    }

    private fun finishGame(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ): Boolean {
        if (currentPlayer.win()) {
            omokView.printOmokBoard(omokBoard.board)
            omokView.result(currentPlayer)
            return true
        }
        return false
    }
}
