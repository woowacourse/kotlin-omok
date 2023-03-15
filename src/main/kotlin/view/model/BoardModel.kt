package view.model

import domain.position.Position

object BoardModel {
    private const val RANGE_MIN = 'A'
    private const val RANGE_MAX = 'O'

    private val range = (RANGE_MIN..RANGE_MAX).toList()
    fun getString(position: Position) = range[position.col] + (Position.POSITION_RANGE.max() - position.row).toString()
    fun getColInt(col: String) = range.indexOf(col.toCharArray()[0]) + 1
}
