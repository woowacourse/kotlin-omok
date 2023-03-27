package omok.controller

import omok.OmokGame
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.player.Stone
import omok.view.InputView
import omok.view.OutputView

class Controller(private val inputView: InputView, private val outputView: OutputView) {

    fun start() {
        val game = OmokGame(Board(), Turn(setOf(Stone.BLACK, Stone.WHITE)))
        outputView.printStart(game.board)
        val selectedPosition = placeFirstStone(game)
        playGame(selectedPosition, game)
        outputView.printWinner(game.currentStone)
    }

    private fun placeFirstStone(game: OmokGame): Position {
        val selectedPosition = Position(inputView.readPosition(game.currentStone))
        game.place(selectedPosition)
        outputView.printBoard(game.board)
        game.changeTurn()
        return selectedPosition
    }

    private fun playGame(initPosition: Position, game: OmokGame) {
        var selectedPosition = initPosition
        while (true) {
            val latestPosition = selectedPosition
            selectedPosition = placeStone(game, latestPosition)
            if (isSuccess(selectedPosition, latestPosition, game)) return
        }
    }

    private fun placeStone(game: OmokGame, latestPosition: Position): Position {
        val selectedPosition = Position(inputView.readPosition(game.currentStone, latestPosition))

        val result = runCatching {
            game.place(selectedPosition)
        }.onFailure {
            println(it.message)
        }.getOrNull()

        if (result == true) return selectedPosition
        return latestPosition
    }

    private fun isSuccess(
        selectedPosition: Position,
        latestPosition: Position,
        game: OmokGame
    ): Boolean {
        if (selectedPosition != latestPosition) {
            outputView.printBoard(game.board)
            game.changeFinishState(selectedPosition)
            if (game.isFinished) return true
            game.changeTurn()
        }
        return false
    }
}
