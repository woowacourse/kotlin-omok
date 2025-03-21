package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokResult
import omok.domain.player.Player
import omok.util.retryInput
import omok.view.InputView
import omok.view.OutputView
import rule.wrapper.point.Point

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val omokGame: OmokGame,
) {
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
        latestPoint: Point?,
        nowPlayer: Player,
    ): OmokResult {
        val thisTurnPoint = playTurn(nowPlayer, latestPoint)
        if (nowPlayer.checkWin(thisTurnPoint)) return OmokResult.getWinner(nowPlayer)
        if (omokGame.isBoardFull()) return OmokResult.DRAW
        return playGame(thisTurnPoint, omokGame.getOtherPlayer(nowPlayer))
    }

    // 플레이어의 한 턴을 처리한다
    private fun playTurn(
        player: Player,
        latestPoint: Point?,
    ): Point {
        outputView.printBoardState(omokGame.grid)
        val point = getPointToPlace(player, latestPoint)
        omokGame.playMove(player, point)
        return point
    }

    // 착수할 위치를 입력 받는다
    private fun getPointToPlace(
        player: Player,
        latestPoint: Point?,
    ): Point {
        return retryInput(
            inputFunction = {
                val point = inputView.getPoint(player, latestPoint, omokGame.grid)
                omokGame.validatePoint(player, point)
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
