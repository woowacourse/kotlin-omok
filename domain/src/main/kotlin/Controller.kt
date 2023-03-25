import domain.Board
import domain.Color
import domain.OmokGame
import domain.RenjuRuleAdapter
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board(rule = RenjuRuleAdapter()))
        OutputView.printCurrentState(omokGame)
        var winnerColor: Color? = null
        while (winnerColor == null) {
            val stone = omokGame.getStone(InputView::inputPosition)
            winnerColor = omokGame.getWinnerColorPhase(
                stone = stone
            )
            OutputView.printCurrentState(omokGame)
        }
        if (winnerColor != null) {
            OutputView.printResult(winnerColor, omokGame.board)
        }
    }
}
