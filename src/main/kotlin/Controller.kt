import domain.OmokGame
import domain.listener.OmokListener
import view.OutputView

class Controller {
    fun run() {
        val omokGame = OmokGame(listener = OmokListener())
        OutputView.printStart()
        omokGame.runGame()
    }
}
