package controller

import GameBoard
import Position
import Stone
import StoneColor
import rule.BlackRenjuRule
import rule.OmokRule
import view.InputView
import view.ResultView

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    private var turnColor: StoneColor = StoneColor.BLACK
    private var rule: OmokRule = BlackRenjuRule()

    fun run() {
        outputView.printGameStartMessage()
        val gameBoard = GameBoard()
        outputView.printGameBoard()
        putStoneUntilFindWinner(gameBoard)
        outputView.printWinner(turnColor)
    }

    private tailrec fun putStoneUntilFindWinner(gameBoard: GameBoard) {
        putStoneProcess(
            gameBoard = gameBoard,
            rule = rule,
            showGameBoardStatus = { outputView.printGameBoard(gameBoard.blackStones + gameBoard.whiteStones) },
        )
        if (gameBoard.judge(rule)) return
        switchTurn()
        putStoneUntilFindWinner(gameBoard)
    }

    private tailrec fun putStoneProcess(
        gameBoard: GameBoard,
        rule: OmokRule,
        showGameBoardStatus: () -> Unit,
    ) {
        val gameState =
            gameBoard.putStone(
                stoneColor = turnColor,
                rule = rule,
                onPositionReceived = { lastStone -> readPositionUntilReceived(lastStone) },
            )

        if (gameState.isSuccess()) {
            showGameBoardStatus()
            return
        }

        outputView.printGameStateMessage(gameState)
        putStoneProcess(gameBoard, rule, showGameBoardStatus)
    }

    private fun readPositionUntilReceived(lastStone: Stone?): Position =
        runCatching {
            inputView.readPosition(turnColor, lastStone)
        }.getOrElse { error ->
            outputView.printErrorMessage(error)
            readPositionUntilReceived(lastStone)
        }

    private fun switchTurn() {
        turnColor = turnColor.switch()
        rule = rule.switch()
    }
}
