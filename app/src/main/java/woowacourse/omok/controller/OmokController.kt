import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.state.Playing

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val rule = OmokRule()
        val board = OmokBoard(rule = rule)
        val game = OmokGame(board)
        outputView.printStartOmok(board.size)
        playOmok(game)
        game.finish { outputView.printWinner(it) }
    }

    private fun playOmok(game: OmokGame) {
        while (game.state is Playing) {
            game.play(
                onTurn = { stoneColor, lastPoint -> outputView.printTurn(stoneColor, lastPoint) },
                onPointSelected = { inputView.getPoint() },
                onForbiddenMove = { outputView.printForbiddenMove(it) },
                onStonePlaced = { board, _ ->
                    outputView.printOmokBoard(board.stones, board.size)
                },
            )
        }
    }
}
