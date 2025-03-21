package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokResult
import omok.domain.StoneState
import omok.domain.point.OmokPoint
import omok.domain.rule.Referee
import omok.util.retryInput
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val omokGame: OmokGame,
) {
    private val referee = Referee()

    fun play() {
        initGame()
        val result = playGame(null, omokGame.getStartingPlayer())
        endGame(result)
    }

    private fun initGame() {
        outputView.printStartMessage()
    }

    // 게임을 진행한다
    private tailrec fun playGame(
        latestPoint: OmokPoint?,
        nowTurn: StoneState,
    ): OmokResult {
        val thisTurnPoint = playTurn(nowTurn, latestPoint)
        if (referee.checkWin(
                omokGame.getRule(nowTurn),
                omokGame.grid.blackStones.stones,
                thisTurnPoint,
            )
        ) {
            return OmokResult.getWinner(nowTurn)
        }
        if (omokGame.isBoardFull()) return OmokResult.DRAW
        return playGame(thisTurnPoint, omokGame.getOtherPlayer(nowTurn))
    }

    // 플레이어의 한 턴을 처리한다
    private fun playTurn(
        turn: StoneState,
        latestPoint: OmokPoint?,
    ): OmokPoint {
        outputView.printBoardState(omokGame.grid)
        val point = getPointToPlace(turn, latestPoint)
        omokGame.playMove(turn, point)
        return point
    }

    // 착수할 위치를 입력 받는다
    private fun getPointToPlace(
        turn: StoneState,
        latestPoint: OmokPoint?,
    ): OmokPoint {
        return retryInput(
            inputFunction = {
                val point = inputView.getPoint(turn, latestPoint)
                omokGame.validatePoint(turn, point)
                point
            },
            printErrorMessage = {
                    message ->
                outputView.printErrorMessage(message)
            },
        )
    }

    private fun endGame(omokResult: OmokResult) {
        outputView.printBoardState(omokGame.grid)
        outputView.printWinner(omokResult)
    }
}
