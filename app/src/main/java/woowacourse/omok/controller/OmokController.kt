// package woowacourse.omok.controller
//
// import woowacourse.omok.domain.OmokAdapter
// import woowacourse.omok.domain.OmokBoard
// import omok.domain.OmokGame
// import omok.domain.Position
// import omok.domain.StoneState
// import omok.domain.turn.PutStoneResult
// import omok.domain.turn.PutStoneResult.Failure
// import omok.domain.turn.PutStoneResult.NextTurn
// import omok.view.InputView
// import omok.view.OutputView
//
// class OmokController(
//    private val inputView: InputView,
//    private val outputView: OutputView,
// ) {
//    private val board = OmokBoard(rule = OmokAdapter())
//    private val omokGame = OmokGame(board)
//
//    fun start() {
//        outputView.printStartMessage()
//        playGame()
//    }
//
//    private fun playGame() {
//        var latestPosition: Position? = null
//        while (true) {
//            val nowTurn = omokGame.getNowTurn()
//            printGameStatus(nowTurn)
//            latestPosition = inputView.getPosition(latestPosition)
//            when (val putResult = omokGame.putStone(latestPosition)) {
//                is NextTurn -> continue
//
//                is PutStoneResult.Finished -> {
//                    printGameResult(omokGame.board, putResult.turn)
//                    return
//                }
//
//                is Failure -> outputView.printError(putResult.message)
//            }
//        }
//    }
//
//    private fun printGameStatus(nowTurn: StoneState) {
//        outputView.printBoardState(omokGame.board)
//        outputView.printTurn(nowTurn)
//    }
//
//    private fun printGameResult(board: OmokBoard, turn: StoneState) {
//        outputView.printBoardState(board)
//        outputView.printWinner(turn)
//    }
// }
