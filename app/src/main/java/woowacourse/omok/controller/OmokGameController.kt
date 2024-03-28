package woowacourse.omok.controller

import omok.model.OmokStone
import omok.model.board.Board
import omok.model.game.InvalidGameRule
import omok.model.game.OmokGame
import omok.model.game.OmokGameResult
import omok.model.game.Placed
import omok.model.game.state.GameState
import woowacourse.omok.console.view.OmokOutputView
import woowacourse.omok.model.PositionUiModel

class OmokGameController(
    private val outputView: OmokOutputView,
    gameState: GameState,
    private val onEndGame: (Board, OmokStone) -> Unit,
) {
    private val game: OmokGame =
        OmokGame(
            gameState,
        ) { board, lastStone ->
            onEndGame(board, lastStone)
            outputView.showGameResult(
                board.toUiModel(),
                lastStone.toUiModel(),
            )
        }

    init {
        outputView.showStartMessage()
        showCurrentGameState(gameState.board, gameState.board.lastStoneOrNull())
    }

    fun placeStone(
        position: PositionUiModel,
        onEndPlaced: (board: Board, lastStone: OmokStone) -> Unit,
        onError: (result: OmokGameResult) -> Unit,
    ) = game.placeStone(
        position.toPosition(),
        onEndPlaced = { board, lastStone ->
            showCurrentGameState(board, lastStone)
            onEndPlaced(board, lastStone)
        },
    ).let {
        onError(it)
        showResult(it)
    }

    private fun showResult(result: OmokGameResult) {
        when (result) {
            Placed -> {}
            InvalidGameRule -> {
                println("게임 룰 위반 다시 두세요")
            }
        }
    }

    private fun showCurrentGameState(
        board: Board,
        stone: OmokStone?,
    ) {
        outputView.showCurrentGameState(board.toUiModel(), stone?.toUiModel())
    }
}
