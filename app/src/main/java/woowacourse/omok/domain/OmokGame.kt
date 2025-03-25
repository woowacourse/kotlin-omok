package woowacourse.omok.domain

class OmokGame(
    private val board: OmokBoard,
) {
    private var nowTurn: StoneState = StoneState.BLACK

    fun getNowTurn(): StoneState = nowTurn

    fun putStone(position: Position): PutStoneResult {
        val stone = Stone(position, nowTurn)
        return when (val putStoneResult = board.putStone(stone)) {
            is PutStoneResult.NextTurn -> {
                changeTurn()
                PutStoneResult.NextTurn(nowTurn)
            }

            else -> putStoneResult
        }
    }

    private fun changeTurn() {
        nowTurn = if (nowTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
    }
}
