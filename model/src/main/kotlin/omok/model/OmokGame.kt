package omok.model

import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.result.ResultHandler
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone.changeStone

class OmokGame {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: () -> Unit,
    ) {
        var stone: GoStone = BlackStone

        while (true) {
            val resultState = retryUntilSuccess { stone.putStone(readPosition(stone)) }
            drawBoard()
            ResultHandler.handleResult(resultState, stone)
            if (isOmok(resultState)) break
            if (!isRunningResult(resultState)) continue
            stone = stone.changeStone()
        }
    }

    private fun isRunningResult(resultState: PutResult) = resultState == PutResult.Running

    private fun isOmok(resultState: PutResult) = resultState == PutResult.OMOK

    private fun <T> retryUntilSuccess(action: () -> T): T =
        runCatching {
            action()
        }.getOrElse {
            println(it.localizedMessage)
            retryUntilSuccess(action)
        }
}
