package controller

import domain.GameBoard
import domain.position.Position
import domain.stone.Stone
import domain.stone.StoneColor
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

    private fun putStoneProcess(
        gameBoard: GameBoard,
        rule: OmokRule,
        showGameBoardStatus: () -> Unit,
    ) {
        gameBoard
            .putStone(
                stoneColor = turnColor,
                rule = rule,
                onPositionReceived = { lastStone -> readPositionUntilReceived(lastStone) },
            ).onSuccess {
                showGameBoardStatus()
            }.onFailure { error ->
                outputView.printErrorMessage(error)
                putStoneProcess(gameBoard, rule, showGameBoardStatus)
            }
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
