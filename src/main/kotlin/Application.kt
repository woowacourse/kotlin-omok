fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val omok = OmokGame(inputView::requestPoint, outputView::printOmokBoardState)
    println(omok.runGame())
}
