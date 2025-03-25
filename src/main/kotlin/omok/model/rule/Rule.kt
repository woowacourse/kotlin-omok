package omok.model.rule

import omok.model.Board
import omok.model.Color
import omok.model.MoveResult
import omok.model.position.Position

interface Rule {
    fun checkViolation(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult

    fun checkOmok(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult
}
