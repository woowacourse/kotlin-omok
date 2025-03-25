package controller

import domain.GameBoard
import domain.player.Player
import domain.position.Position
import domain.stone.Stone
import domain.stone.StoneColor
import rule.adapter.RuleAdapter
import view.InputView
import view.ResultView

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    fun run() {
        outputView.printGameStartMessage()
        val gameBoard = GameBoard(players())
        outputView.printGameBoard()
        putStoneUntilFindWinner(gameBoard)
        outputView.printWinner(gameBoard.winner())
    }

    private fun players(): ArrayDeque<Player> {
        val players = ArrayDeque<Player>()
        players.addAll(
            listOf(
                Player(
                    stoneColor = StoneColor.BLACK,
                    rules = listOf(RuleAdapter()),
                ),
                Player(
                    stoneColor = StoneColor.WHITE,
                    rules = listOf(RuleAdapter()),
                ),
            ),
        )
        return players
    }

    private tailrec fun putStoneUntilFindWinner(gameBoard: GameBoard) {
        putStoneProcess(
            gameBoard = gameBoard,
            showGameBoardStatus = { outputView.printGameBoard(gameBoard.placedAllStones()) },
        )
        if (gameBoard.gameOver()) return
        gameBoard.nextTurn()
        putStoneUntilFindWinner(gameBoard)
    }

    private fun putStoneProcess(
        gameBoard: GameBoard,
        showGameBoardStatus: () -> Unit,
    ) {
        gameBoard
            .putStone(
                onPositionReceived = { stoneColor, lastStone -> readPositionUntilReceived(stoneColor, lastStone) },
            ).onSuccess {
                showGameBoardStatus()
            }.onFailure { error ->
                outputView.printErrorMessage(error)
                putStoneProcess(gameBoard, showGameBoardStatus)
            }
    }

    private fun readPositionUntilReceived(
        stoneColor: StoneColor,
        lastStone: Stone?,
    ): Position =
        runCatching {
            inputView.readPosition(stoneColor, lastStone)
        }.getOrElse { error ->
            outputView.printErrorMessage(error)
            readPositionUntilReceived(stoneColor, lastStone)
        }
}
