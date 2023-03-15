class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) : Runnable {
    override fun run() {
        val omokGame = OmokGame(inputView::requestPoint, outputView::printOmokBoardState)
        val result = omokGame.runGame()
        outputView.printWinner(result)
    }
}
