import domain.Board
import domain.OmokGame
import domain.RenjuRuleAdapter
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board(rule = RenjuRuleAdapter()))
        OutputView.printCurrentState(omokGame)
        while (omokGame.isRunning()) {
            val position = InputView.inputPosition()
            val stone = omokGame.getStone(position)
            val isSuccess = omokGame.placeTo(stone)
            if (isSuccess) OutputView.printCurrentState(omokGame)
        }
        val winner = omokGame.getWinnerColor()
        if (winner != null) OutputView.printResult(winner, omokGame.board)
    }
}
