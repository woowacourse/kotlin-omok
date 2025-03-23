package controller

import GameBoard
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
        while (true) {
            putStoneProcess(
                gameBoard = gameBoard,
                rule = rule,
                showGameBoardStatus = { outputView.printGameBoard(gameBoard.blackStones + gameBoard.whiteStones) },
            )
            if (gameBoard.judge(rule)) break
            switchTurn()
        }
        outputView.printWinner(turnColor)
    }

    private fun putStoneProcess(
        gameBoard: GameBoard,
        rule: OmokRule,
        showGameBoardStatus: () -> Unit,
    ) {
        val gameState =
            gameBoard.putStone(
                stoneColor = turnColor,
                rule = rule,
                onPositionReceived = { lastStone -> inputView.readInputPosition(turnColor, lastStone) },
            )
        if (gameState.isFail()) {
            outputView.printGameStateMessage(gameState)
            putStoneProcess(gameBoard, rule, showGameBoardStatus)
        } else {
            showGameBoardStatus()
        }
    }

    private fun switchTurn() {
        turnColor = turnColor.switch()
        rule = rule.switch()
    }
}
