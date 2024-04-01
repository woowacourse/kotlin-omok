package woowacourse.omok.presenter

import android.util.Log
import omock.model.Position
import omock.model.game.OmokGame
import omock.model.onFailure
import omock.model.onSuccess
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.view.OmokGameConsoleView
import woowacourse.omok.view.OmokGameAndroidView
import woowacourse.omok.view.PlaceErrorHandler

class OmokGamePresenter(
    private val view: OmokGameAndroidView,
    private val consoleView: OmokGameConsoleView,
    private val repository: OmokRepository,
    private val errorHandler: PlaceErrorHandler,
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
                println(game.lastGameResult())
                view.showCurrentGameState(board, lastBlock.toAndroid())
                consoleView.showCurrentGameState(board.toConsole(), lastBlock.toConsole())
                if (game.isEnd()) {
                    view.showGameResult(board, lastBlock.toAndroid())
                    consoleView.showGameResult(board.toConsole(), lastBlock.toConsole())
                }
            }.onFailure {
                val errorMessage = errorHandler.onError(it)
                view.showPlaceError(errorMessage)
                consoleView.showPlaceError(errorMessage)
            }
    }

    fun resetGame() {
        repository.resetGame()
        view.resetView()
        startGame()
    }
}
