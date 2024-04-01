package woowacourse.omok.presenter

import omock.model.Position
import omock.model.game.OmokGame
import omock.model.onFailure
import omock.model.onSuccess
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.view.OmokGameAndroidView
import woowacourse.omok.view.OmokGameConsoleView

class OmokGamePresenter(
    private val view: OmokGameAndroidView,
    private val consoleView: OmokGameConsoleView,
    private val repository: OmokRepository,
) {
    private lateinit var game: OmokGame

    init {
        startGame()
    }

    private fun startGame() {
        game = repository.fetchGame()
        val board = game.currentBoard()
        view.showGameStart(board)
        consoleView.showGameStart(board.toConsole())
    }

    fun placeStone(position: Position) {
        game.placeStone(position)
            .onSuccess {
                val (board, lastBlock) = game.lastGameResult()
                repository.saveGameTurn(lastBlock)
                view.showCurrentGameState(board, lastBlock.toAndroid())
                consoleView.showCurrentGameState(board.toConsole(), lastBlock.toConsole())
                if (game.isEnd()) {
                    view.showGameResult(board, lastBlock.toAndroid())
                    consoleView.showGameResult(board.toConsole(), lastBlock.toConsole())
                }
            }.onFailure {
                view.showPlaceError(it.toAndroidErrorMessage())
                consoleView.showPlaceError(it.toConsoleErrorMessage())
            }
    }

    fun resetGame() {
        repository.resetGame()
        view.resetView()
        startGame()
    }
}
