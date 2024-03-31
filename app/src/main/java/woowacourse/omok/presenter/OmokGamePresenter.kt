package woowacourse.omok.presenter

import omock.model.Position
import omock.model.game.OmokGame
import omock.model.onFailure
import omock.model.onSuccess
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.view.ConsoleOmokGameView
import woowacourse.omok.view.OmokGameView
import woowacourse.omok.view.PlaceErrorHandler

class OmokGamePresenter(
    private val view: OmokGameView,
    private val consoleView: ConsoleOmokGameView,
    private val repository: OmokRepository,
    private val errorHandler: PlaceErrorHandler,
) {
    private lateinit var game: OmokGame

    init {
        startGame()
    }

    fun startGame() {
        game = repository.fetchGame()
        val board = game.currentBoard()
        view.showGameStart(board)
        consoleView.showGameStart(board.toUiModel())
    }

    fun placeStone(position: Position) {
        game.placeStone(position)
            .onSuccess {
                val (board, lastBlock) = game.lastGameResult()
                repository.saveGame(lastBlock)
                view.showCurrentGameState(board, lastBlock)
                consoleView.showCurrentGameState(board.toUiModel(), lastBlock.toUiModel())
                if (game.isEnd()) {
                    view.showGameResult(board, lastBlock)
                    consoleView.showGameResult(board.toUiModel(), lastBlock.toUiModel())
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
