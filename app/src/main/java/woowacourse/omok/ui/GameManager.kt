package woowacourse.omok.ui

import woowacourse.omok.data.repository.Repository
import woowacourse.omok.model.Board
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.state.GameState

class GameManager(
    private val gamePlayHandler: GamePlayHandler,
    private val repository: Repository,
) {
    private var gameState: GameState = GameState.Playing.Start(Board())

    fun playTurn(coordinate: Coordinate) {
        gameState = gameState.placeStone(coordinate)
        gamePlayHandler.onUpdate(gameState)
        saveCoordinate(coordinate)
    }

    fun loadGame() =
        runCatching {
            gameState = GameState.Playing.Start(Board())
            repository.findAll().forEach {
                gameState = gameState.placeStone(it)
            }
            gamePlayHandler.onUpdate(gameState)
        }.onFailure {
            gamePlayHandler.onError(it)
        }

    fun replay() {
        gameState = GameState.Playing.Start(Board())
        gamePlayHandler.onUpdate(gameState)
        deleteAllCoordinates()
    }

    private fun saveCoordinate(coordinate: Coordinate) =
        runCatching {
            repository.save(coordinate)
        }.onFailure {
            gamePlayHandler.onError(it)
        }

    private fun deleteAllCoordinates() =
        runCatching {
            repository.drop()
        }.onFailure {
            gamePlayHandler.onError(it)
        }
}
