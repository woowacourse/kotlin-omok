package woowacourse.omok.domain

import woowacourse.omok.domain.turn.PutStoneResult
import woowacourse.omok.domain.turn.TurnManager

class OmokGame(
    val board: OmokBoard,
    private val turnManager: TurnManager = TurnManager(),
) {
    fun getNowTurn(): StoneState = turnManager.nowTurn

    fun putStone(position: Position): PutStoneResult {
        val nowTurn = turnManager.nowTurn
        val stone = Stone(position, nowTurn)

        val putStoneResult = board.putStone(stone)
        when (putStoneResult) {
            is PutStoneResult.Finished -> return PutStoneResult.Finished(nowTurn)

            is PutStoneResult.InvalidPosition -> return PutStoneResult.InvalidPosition
            is PutStoneResult.AlreadyPlaced -> return PutStoneResult.AlreadyPlaced
            is PutStoneResult.Violation -> return PutStoneResult.Violation

            is PutStoneResult.NextTurn -> {
                turnManager.changeTurn()
                return PutStoneResult.NextTurn(turnManager.nowTurn)
            }
        }
    }
}
