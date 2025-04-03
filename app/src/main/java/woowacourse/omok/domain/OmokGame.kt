package woowacourse.omok.domain

class OmokGame(
    private val board: OmokBoard,
    var turn: StoneState = StoneState.BLACK,
) {
    fun getState(position: Position) = board.getStoneState(position)

    fun putStone(stone: Stone): PutStoneResult = board.putStone(stone)

    fun changeTurn() {
        this.turn = if (turn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
    }
}
