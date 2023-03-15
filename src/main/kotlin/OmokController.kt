class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) {
    fun run(): Color {
        return OmokGame(inputView::requestPoint, outputView::printOmokBoardState).runGame()
    }
}
