package domain

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val referee: Referee = Referee(),
    private val omokGameListener: Listener
) {
    fun runGame() {
        while (true) {
            successBlackTurn()
            if (isVictory(State.BLACK)) break

            successWhiteTurn()
            if (isVictory(State.WHITE)) break
        }
    }

    private fun isVictory(state: State): Boolean {
        if (referee.isWin(omokBoard, state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }

    private fun successBlackTurn(): Boolean {
        val blackStone = omokGameListener.onStoneRequest()
        if (!omokBoard.isEmpty(blackStone)) {
            omokGameListener.onMoveFail()
            return successBlackTurn()
        }

        if (!referee.isMovable(omokBoard, blackStone)) {
            omokGameListener.onForbidden()
            return successBlackTurn()
        }
        omokBoard.move(blackStone, State.BLACK)
        omokGameListener.onMove(omokBoard, State.WHITE, blackStone)
        return true
    }

    private fun successWhiteTurn(): Boolean {
        val whiteStone = omokGameListener.onStoneRequest()
        if (!omokBoard.isEmpty(whiteStone)) {
            omokGameListener.onMoveFail()
            return successWhiteTurn()
        }
        omokBoard.move(whiteStone, State.WHITE)
        omokGameListener.onMove(omokBoard, State.BLACK, whiteStone)
        return true
    }
}
