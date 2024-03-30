package woowacourse.omok

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.player.Player

enum class PlaceType {
    CANNOT_PLACE,
    WHITE_PLACE,
    BLACK_PLACE,
}

class OmokGame2(
    private val board: Board,
    private val omokPlayers: OmokPlayers,
    private val finishAction: FinishAction,
) {
    private var turnPlayer = omokPlayers.firstOrderPlayer()

    fun nextTurn(position: Position): PlaceType {
        val stone = turnPlayer.stone
        runCatching { board.place(position, turnPlayer) }
            .onFailure { return PlaceType.CANNOT_PLACE }

        turnPlayer = omokPlayers.next(turnPlayer)
        val finishType = finishType(board, position, turnPlayer)
        finishAction.onFinish(finishType)

        if (stone == Stone.WHITE) return PlaceType.WHITE_PLACE
        return PlaceType.BLACK_PLACE
    }

    private fun finishType(
        board: Board,
        recentPosition: Position,
        player: Player,
    ): FinishType {
        return finishAction.conditions
            .map { it.finishType(board, recentPosition, player) }
            .minBy { it.ordinal }
    }
}
