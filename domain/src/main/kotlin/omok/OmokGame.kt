package omok

import omok.gameState.BlackTurn
import omok.gameState.GameState
import omok.state.StoneState

private typealias GetOmokPoint = (StoneState, OmokPoint?) -> OmokPoint
private typealias OutputBoardStatus = (OmokBoard) -> Unit

class OmokGame {
    var gameState: GameState = BlackTurn(OmokBoard())
    tailrec fun play(getOmokPoint: GetOmokPoint, outputBoardStatus: OutputBoardStatus, point: OmokPoint? = null) {
        var tempPoint: OmokPoint? = getOmokPoint(gameState.stoneState, point)
        runCatching {
            require(tempPoint in gameState.omokBoard.value) { ERROR_POINT_OVER.format(tempPoint!!.x, tempPoint!!.y) }
            gameState = gameState.play(tempPoint!!)
            outputBoardStatus(gameState.omokBoard)
        }
            .onFailure {
                println(it.message)
                tempPoint = point
            }
        if (gameState.isRunning) play(getOmokPoint, outputBoardStatus, tempPoint)
    }

    companion object {
        private const val ERROR_POINT_OVER = "(%s, %s)는 오목판을 벗어납니다."
    }
}
