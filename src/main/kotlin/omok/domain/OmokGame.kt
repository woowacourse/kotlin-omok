package omok.domain

import omok.domain.turn.PutStoneResult
import omok.domain.turn.PutStoneResult.Success.Finished
import omok.domain.turn.PutStoneResult.Success.NextTurn
import omok.domain.turn.TurnManager

class OmokGame(
    val board: OmokBoard,
    private val turnManager: TurnManager,
) {
    fun putStone(position: Position): PutStoneResult {
        val nowTurn = turnManager.nowTurn
        val stone = Stone(position, nowTurn)

        when (nowTurn) {
            StoneState.BLACK -> {
                if (board.isStonePlaced(position)) return PutStoneResult.Failure(ERROR_STONE_ALREADY_PUT)
                if (board.invalidPlace(stone)) return PutStoneResult.Failure(ERROR_INVALID_POSITION)
            }

            StoneState.WHITE ->
                if (board.isStonePlaced(position)) {
                    return PutStoneResult.Failure(
                        ERROR_STONE_ALREADY_PUT,
                    )
                }

            StoneState.BLANK -> throw IllegalStateException()
        }

        board.putStone(stone)

        if (board.checkOmok(position)) return PutStoneResult.Success(Finished(nowTurn))

        turnManager.changeTurn()
        return PutStoneResult.Success(NextTurn(turnManager.nowTurn))
    }

    companion object {
        const val ERROR_INVALID_POSITION = "잘못된 위치입니다. 다시 입력해주세요."
        const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다. 다시 입력해주세요."
    }
}
