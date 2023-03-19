package model.domain.state

import model.domain.rule.OmokRule
import model.domain.tools.Board
import model.domain.tools.Location

abstract class Turn(override val board: Board) : State {
    abstract override fun place(location: Location): State
    protected fun isOmok(location: Location): Boolean = OmokRule.isOmok(board, location, stone)
    abstract fun isForbidden(location: Location): Boolean
}
