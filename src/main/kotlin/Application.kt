fun main() {
    val rows = 15
    val cols = 15
    val letters = "ABCDEFGHIJKLMNOP"
    val inputView = InputView()
    val outputView = OutputView()
    val omok = OmokGame(inputView::requestPoint, outputView::printOmokBoardState)
    println(omok.runGame())
}
