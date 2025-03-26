package woowacourse.omok.domain

class OmokGame(
    private val board: OmokBoard,
    var turn: StoneState = StoneState.BLACK,
) {
    fun putStone(stone: Stone): PutStoneResult =
        when (val putStoneResult = board.putStone(stone)) {
            is PutStoneResult.NextTurn -> {
                PutStoneResult.NextTurn(turn)
            }

            else -> putStoneResult
        }

    fun changeTurn() {
        this.turn = if (turn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
    }
}
