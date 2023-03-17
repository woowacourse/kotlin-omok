package domain

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val referee: Referee = Referee()
) {
    fun runGame(
        getStone: () -> Stone,
        onMove: (OmokBoard, State, Stone) -> Unit,
        onMoveFail: () -> Unit,
        onForbidden: () -> Unit,
        onFinish: (State) -> Unit
    ) {
        while (true) {
            successBlackTurn(getStone, onMoveFail, onForbidden, onMove)
            if (isVictory(State.BLACK, onFinish)) break

            successWhiteTurn(getStone, onMoveFail, onMove)
            if (isVictory(State.WHITE, onFinish)) break
        }
    }

    private fun isVictory(state: State, onFinish: (State) -> Unit): Boolean {
        if (referee.isWin(omokBoard, state)) {
            onFinish(state)
            return true
        }
        return false
    }

    private fun successBlackTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onForbidden: () -> Unit,
        onMove: (OmokBoard, State, Stone) -> Unit
    ): Boolean {
        val blackStone = getStone()
        if (!omokBoard.isEmpty(blackStone)) {
            onMoveFail()
            return successBlackTurn(getStone, onMoveFail, onForbidden, onMove)
        }

        if (!referee.isMovable(omokBoard, blackStone)) {
            onForbidden()
            return successBlackTurn(getStone, onMoveFail, onForbidden, onMove)
        }
        omokBoard.move(blackStone, State.BLACK)
        onMove(omokBoard, State.WHITE, blackStone)
        return true
    }

    private fun successWhiteTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onMove: (OmokBoard, State, Stone) -> Unit
    ): Boolean {
        val whiteStone = getStone()
        if (!omokBoard.isEmpty(whiteStone)) {
            onMoveFail()
            return successWhiteTurn(getStone, onMoveFail, onMove)
        }
        omokBoard.move(whiteStone, State.WHITE)
        onMove(omokBoard, State.BLACK, whiteStone)
        return true
    }
}
