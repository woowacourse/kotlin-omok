package woowacourse.omok

import domain.domain.*
import domain.library.combinerule.CombinedRuleAdapter
import domain.view.OmokView
import domain.view.OutputView

class AndroidController {

    lateinit var omokGame: OmokGame
    fun run() {
        omokGame = OmokGame(gameRule = CombinedRuleAdapter())

    }

    fun progressGame(omokGame: OmokGame, position: Position) {
        when (omokGame.progressTurn(position)) {
            ProgressState.ERROR -> {
//                OutputView.printError()
//                OutputView.printTurn(turn)
//                OutputView.printLastPosition(board.lastPosition)
//                OutputView.printRequestPosition()
//                해야할일들
            }
//            ProgressState.END -> OmokView.printResult(omokGame.board, omokGame.turn) 해야할일들
            ProgressState.CONTINUE -> {
//                OutputView.printTurn(turn)
//                OutputView.printLastPosition(board.lastPosition)
//                OutputView.printRequestPosition()
//                해야할일듯
            }
        }
    }
}
