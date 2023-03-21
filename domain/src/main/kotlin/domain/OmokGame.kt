package domain

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val referee: Referee = Referee(),
    private val omokGameListener: OmokGameListener
) {

    fun isVictory(state: State): Boolean {
        if (referee.isWin(omokBoard, state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }

    fun successTurn(stone: Stone, turn: State): Boolean {
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return false
        }
        if (turn == State.BLACK) return successBlackTurn(stone)
        return successWhiteTurn(stone)
    }

    private fun successBlackTurn(blackStone: Stone): Boolean {
        if (!referee.isMovable(omokBoard, blackStone)) {
            omokGameListener.onForbidden()
            return false
        }
        omokBoard.move(blackStone, State.BLACK)
        omokGameListener.onMove(omokBoard, State.WHITE, blackStone)
        return true
    }

    private fun successWhiteTurn(whiteStone: Stone): Boolean {
        omokBoard.move(whiteStone, State.WHITE)
        omokGameListener.onMove(omokBoard, State.BLACK, whiteStone)
        return true
    }
}
