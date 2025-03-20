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
        var currentPlayer: Player = BlackPlayer(BlackPlayerState()) // 시작은 흑돌 플레이어
        playGame(currentPlayer, omokBoard)
        omokView.result(currentPlayer)
    }

    private fun playGame(
        player: Player,
        omokBoard: OmokBoard,
    ) {
        var currentPlayer = player
        while (!currentPlayer.isFinish()) {
            val position = omokView.inputPosition(currentPlayer)
            currentPlayer.put(position, omokBoard)
            if (currentPlayer.isFinish()) {
                omokView.printOmokBoard(omokBoard.board())
                break
            }
            omokView.printOmokBoard(omokBoard.board())
            currentPlayer = currentPlayer.nextTurn()
        }
    }
}
