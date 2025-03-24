package omok.controller

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.rule.OmokRule
import omok.domain.service.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val playingBoard = PlayingBoard(OmokBoard.create(), OmokRule.rules)
        outputView.displayOmokGameStart()
        outputView.displayOmokBoard(playingBoard.board)
        OmokGame(playingBoard).start(inputView::askForPosition) { placeResult ->
            handlePlaceResult(playingBoard.board, placeResult)
        }
    }

    private fun handlePlaceResult(
        omokBoard: OmokBoard,
        placeResult: PlaceResult,
    ) {
        outputView.displayOmokBoard(omokBoard)
        when (placeResult) {
            is Success.Progress -> return
            is Success.Finish -> outputView.displayGameResultMessage(placeResult.gameResult)
            is Failure.AlreadyExistStone, is Failure.InvalidPosition -> outputView.displayMisPlaceMessage(placeResult)
            is Failure.ExternalRenjuRule -> outputView.displayForbiddenMessage(placeResult.rule)
        }
    }
}
