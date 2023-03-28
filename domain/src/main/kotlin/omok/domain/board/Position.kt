package omok.domain.board

import omok.domain.board.Column.Companion.toColumn
import omok.domain.board.Row.Companion.toRow

internal fun String?.toPosition(): Position {
    val columnText = this?.substring(0, 1)
    val rowText = this?.substring(1)
    val column = columnText.toColumn()
    val row = rowText.toRow()

    require(column != null && row != null) { "[ERROR] ${this ?: ""} 라는 위치는 존재하지 않습니다." }
    return Position(column, row)
}

data class Position(val column: Column, val row: Row) {

    private constructor(columnAxis: Int, rowAxis: Int) : this(toColumn(columnAxis), toRow(rowAxis))

    fun getNorth(): Position? {
        val northRow = row.up()
        if (northRow != null) return copy(row = northRow)
        return null
    }

    fun getNorthEast(): Position? {
        val eastColumn = column.right()
        val northRow = row.up()
        if (eastColumn != null && northRow != null) return copy(column = eastColumn, row = northRow)
        return null
    }

    fun getEast(): Position? {
        val eastColumn = column.right()
        if (eastColumn != null) return copy(column = eastColumn)
        return null
    }

    fun getSouthEast(): Position? {
        val eastColumn = column.right()
        val southRow = row.down()
        if (eastColumn != null && southRow != null) return copy(column = eastColumn, row = southRow)
        return null
    }

    fun getSouth(): Position? {
        val southRow = row.down()
        if (southRow != null) return copy(row = southRow)
        return null
    }

    fun getSouthWest(): Position? {
        val westColumn = column.left()
        val southRow = row.down()
        if (westColumn != null && southRow != null) return copy(column = westColumn, row = southRow)
        return null
    }

    fun getWest(): Position? {
        val westColumn = column.left()
        if (westColumn != null) return copy(column = westColumn)
        return null
    }

    fun getNorthWest(): Position? {
        val westColumn = column.left()
        val northRow = row.up()
        if (westColumn != null && northRow != null) return copy(column = westColumn, row = northRow)
        return null
    }

    companion object {
        fun of(index: Int): Position {
            val columnAxis = index % 15
            val rowAxis = 15 - (index / 15) - 1
            return Position(columnAxis, rowAxis)
        }
    }
}
