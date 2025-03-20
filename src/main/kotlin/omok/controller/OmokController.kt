package omok.controller

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.rule.PlaceResult
import omok.domain.service.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val playingBoard = PlayingBoard(OmokBoard.create())
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
            is PlaceResult.Success.Progress -> return
            is PlaceResult.Success.Finish -> outputView.displayWinningMessage(placeResult.winning)
            is PlaceResult.Failure -> outputView.displayErrorMessage(placeResult)
        }
    }
}
