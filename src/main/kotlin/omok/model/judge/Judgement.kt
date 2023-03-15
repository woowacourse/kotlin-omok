package omok.model.judge

import omok.model.Board
import omok.model.GoStone
import omok.model.state.State

class Judgement(
    private val goStone: GoStone,
    private val board: Board
) {
    fun check(): State {
        return JudgeWin.checkWin(goStone, board)
    }
}
