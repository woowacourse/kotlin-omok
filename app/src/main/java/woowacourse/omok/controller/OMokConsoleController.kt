package woowacourse.omok.controller

import woowacourse.omok.model.GameState
import woowacourse.omok.model.GameTurn
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.view.InputView
import woowacourse.omok.view.LocalBoard
import woowacourse.omok.view.OutputView.outputBoardForm
import woowacourse.omok.view.OutputView.outputFailureMessage
import woowacourse.omok.view.OutputView.outputGameStart
import woowacourse.omok.view.OutputView.outputLastStone
import woowacourse.omok.view.OutputView.outputSuccessOMock
import woowacourse.omok.view.OutputView.outputUserTurn

class OMokConsoleController : OMockGame() {
    init {
        startGameBoard()
    }

    override fun executePlayerSuccessStep(
        playerStone: Stone,
        player: Player,
    ) {
        LocalBoard.setBoardIcon(playerStone, player)
        player.stoneHistory.add(playerStone)
    }

    override fun executePlayerPickFailStep(throwable: Throwable) {
        outputFailureMessage(throwable)
    }

    fun run() {
        outputGameStart()
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        while (true) {
            outputBoardForm()
            when (board.getTurn()) {
                GameTurn.BLACK_TURN -> userTurnFlow(blackPlayer)
                GameTurn.WHITE_TURN -> userTurnFlow(whitePlayer)
                GameTurn.FINISHED -> {
                    outputSuccessOMock()
                    break
                }
            }
        }
    }

    private fun userTurnFlow(player: Player) {
        outputUserTurn(player)
        outputLastStone(player.stoneHistory.lastOrNull())
        playerPick(player = player)
    }

    private fun playerPick(player: Player) {
        when(val playerStone = InputView.playerPick(player = player)){
            is GameState.LoadStone.Success -> start(player, playerStone.stone)
            is GameState.LoadStone.Failure -> executePlayerPickFailStep(playerStone.throwable)
        }
    }
}
