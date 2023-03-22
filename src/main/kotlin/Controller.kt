package domain

import domain.domain.ProgressState.CONTINUE
import domain.domain.ProgressState.END
import domain.domain.ProgressState.ERROR
import domain.library.ark.CombinedRuleAdapter
import view.OmokView
import view.OutputView

class Controller {
    fun run() {
        val omokGame = OmokGame(gameRule = CombinedRuleAdapter())
        OmokView.printStart()
        progressGame(omokGame)
    }

    private fun progressGame(omokGame: OmokGame) {
        when (omokGame.progressTurn(OmokView.putPhase(omokGame.board, omokGame.turn))) {
            ERROR -> {
                OutputView.printError()
                progressGame(omokGame)
            }

            END -> OmokView.printResult(omokGame.board, omokGame.turn)
            CONTINUE -> progressGame(omokGame)
        }
    }
}
