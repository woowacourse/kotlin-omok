package omok.controller

import omok.model.board.OmokBoard
import omok.model.player.Player
import omok.view.OmokView

class OmokController(
    private val omokView: OmokView,
) {
    fun run() {
        omokView.printStartMessage()
        val omokBoard = OmokBoard()
        val player = Player()
        playGame(player, omokBoard)
    }

    private fun playGame(
        player: Player,
        omokBoard: OmokBoard,
    ) {
        while (player.playing()) {
            playerTurn(player, omokBoard)
            if (finishGame(player, omokBoard)) break
            omokView.printOmokBoard(omokBoard.board)
            player.nextPlayer()
        }
    }

    private fun handleTurnException(inputStone: () -> Unit) {
        runCatching {
            inputStone()
        }.onFailure { error ->
            println(error.message)
            handleTurnException(inputStone)
        }
    }

    private fun playerTurn(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ) {
        handleTurnException {
            val position = omokView.inputPosition(currentPlayer.playerState)
            currentPlayer.put(position, omokBoard)
        }
    }

    private fun finishGame(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ): Boolean {
        if (currentPlayer.win()) {
            omokView.printOmokBoard(omokBoard.board)
            omokView.result(currentPlayer.playerState)
            return true
        }
        return false
    }
}
