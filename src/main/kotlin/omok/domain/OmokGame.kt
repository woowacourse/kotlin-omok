package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

private typealias GetOmokPoint = (StoneState, OmokPoint?) -> OmokPoint
private typealias OutputBoardStatus = (OmokBoard) -> Unit

class OmokGame {
    var point: OmokPoint? = null
    var gameState: GameState = BlackTurn(OmokBoard())
    fun play(getOmokPoint: GetOmokPoint, outputBoardStatus: OutputBoardStatus) {
        val tempPoint = getOmokPoint(gameState.stoneState, point)
        runCatching {
            gameState = gameState.play(tempPoint)
        }
            .onFailure {
                println(it.message)
            }
            .onSuccess {
                point = tempPoint
            }
        outputBoardStatus(gameState.omokBoard)
        when {
            gameState.isRunning -> play(getOmokPoint, outputBoardStatus)
            else -> return
        }
    }
}
