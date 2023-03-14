fun main() {
    val rows = 15
    val cols = 15
    val letters = "ABCDEFGHIJKLMNOP"

    val omok = OmokGame()
    val inputView = InputView()
    val outputView = OutputView()
    omok.runGame(inputView::requestPoint, outputView::printOmokBoardState)
}
