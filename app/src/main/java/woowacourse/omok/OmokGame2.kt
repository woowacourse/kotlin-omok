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

    fun turn(position: Position): PlaceType {
        return placeType(position, turnPlayer).also {
            finishAction.onFinish(finishType(board, position, turnPlayer))
            nextPlayer(it)
        }
    }

    private fun placeType(position: Position, turnPlayer: Player): PlaceType {
        runCatching { board.place(position, turnPlayer) }
            .onFailure { return PlaceType.CANNOT_PLACE }
        return if (turnPlayer.stone == Stone.WHITE) PlaceType.WHITE_PLACE else PlaceType.BLACK_PLACE
    }

    private fun nextPlayer(placeType: PlaceType) {
        if (placeType == PlaceType.CANNOT_PLACE) return
        turnPlayer = omokPlayers.next(turnPlayer)
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
