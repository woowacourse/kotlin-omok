package omok.domain

import omok.domain.turn.PutStoneResult
import omok.domain.turn.TurnManager

class OmokGame(
    val board: OmokBoard,
    private val turnManager: TurnManager = TurnManager(),
) {
    fun getNowTurn(): StoneState = turnManager.nowTurn

    fun putStone(position: Position): PutStoneResult {
        val nowTurn = turnManager.nowTurn
        val stone = Stone(position, nowTurn)

        val putStoneResult = getPutStoneResult(stone)
        if (putStoneResult is PutStoneResult.Failure) return putStoneResult

        board.putStone(stone)
        if (board.checkOmok(position)) return PutStoneResult.Finished(nowTurn)

        turnManager.changeTurn()
        return PutStoneResult.NextTurn(turnManager.nowTurn)
    }

    private fun getPutStoneResult(stone: Stone): PutStoneResult {
        return when (stone.state) {
            StoneState.BLACK -> {
                if (board.isStonePlaced(stone.position)) {
                    PutStoneResult.Failure(ERROR_STONE_ALREADY_PUT)
                } else if (board.invalidPlace(stone)) {
                    PutStoneResult.Failure(ERROR_INVALID_POSITION)
                } else {
                    PutStoneResult.NextTurn(stone.state)
                }
            }

            StoneState.WHITE -> {
                if (board.isStonePlaced(stone.position)) {
                    PutStoneResult.Failure(ERROR_STONE_ALREADY_PUT)
                } else {
                    PutStoneResult.NextTurn(stone.state)
                }
            }

            StoneState.BLANK -> throw IllegalStateException()
        }
    }

    companion object {
        const val ERROR_INVALID_POSITION = "잘못된 위치입니다. 다시 입력해주세요."
        const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다. 다시 입력해주세요."
    }
}
