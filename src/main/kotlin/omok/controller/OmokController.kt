package omok.controller

import omok.model.Omok
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
        val omokBoard = OmokBoard(Omok())
        val currentPlayer: Player = BlackPlayer(BlackPlayerState()) // 시작은 흑돌 플레이어
        playGame(currentPlayer, omokBoard)
        omokView.result(currentPlayer)
    }

    private fun playGame(
        player: Player,
        omokBoard: OmokBoard,
    ) {
        var currentPlayer = player
        while (true) {
            playerTurn(currentPlayer, omokBoard)
            if (finishGame(currentPlayer, omokBoard)) break
            omokView.printOmokBoard(omokBoard.board())
            currentPlayer = currentPlayer.nextTurn()
        }
    }

    private fun playerTurn(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ) {
        runCatching {
            val position = omokView.inputPosition(currentPlayer)
            currentPlayer.put(position, omokBoard)
        }.getOrElse { error ->
            println(error.message)
            playerTurn(currentPlayer, omokBoard)
        }
    }

    private fun finishGame(
        currentPlayer: Player,
        omokBoard: OmokBoard,
    ): Boolean {
        if (currentPlayer.isFinish()) {
            omokView.printOmokBoard(omokBoard.board())
            return true
        }
        return false
    }
}
