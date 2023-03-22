package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

private typealias GetOmokPoint = (StoneState, OmokPoint?) -> OmokPoint
private typealias OutputBoardStatus = (OmokBoard) -> Unit

class OmokGame {
    var gameState: GameState = BlackTurn(OmokBoard())
    tailrec fun play(getOmokPoint: GetOmokPoint, outputBoardStatus: OutputBoardStatus, point: OmokPoint? = null) {
        var tempPoint: OmokPoint? = getOmokPoint(gameState.stoneState, point)
        runCatching {
            gameState = gameState.play(tempPoint!!)
            outputBoardStatus(gameState.omokBoard)
        }
            .onFailure {
                println(it.message)
                tempPoint = point
            }
        if (gameState.isRunning) play(getOmokPoint, outputBoardStatus, tempPoint)
    }
}
