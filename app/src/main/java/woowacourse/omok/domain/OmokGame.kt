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
                PutStoneResult.NextTurn(nowTurn)
            }

            else -> putStoneResult
        }
    }

    fun changeTurn() {
        this.nowTurn = if (nowTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
    }
}
