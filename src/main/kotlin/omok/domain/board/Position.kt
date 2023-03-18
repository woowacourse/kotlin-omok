package omok.domain.board

import omok.domain.board.Column.Companion.toColumn
import omok.domain.board.Row.Companion.toRow

fun Pair<Int, Int>.toPosition(): Position {
    val column = toColumn(this.first)
    val row = toRow(this.second)

    return Position(column, row)
}

data class Position(val column: Column, val row: Row) {

    fun getNorth(): Position? {
        return this.copy(row = row.up() ?: return null)
    }

    fun getNorthEast(): Position? {
        return this.copy(column = column.right() ?: return null, row = row.up() ?: return null)
    }

    fun getEast(): Position? {
        return this.copy(column = column.right() ?: return null)
    }

    fun getSouthEast(): Position? {
        return this.copy(column = column.right() ?: return null, row = row.down() ?: return null)
    }

    fun getSouth(): Position? {
        return this.copy(row = row.down() ?: return null)
    }

    fun getSouthWest(): Position? {
        return this.copy(column = column.left() ?: return null, row = row.down() ?: return null)
    }

    fun getWest(): Position? {
        return this.copy(column = column.left() ?: return null)
    }

    fun getNorthWest(): Position? {
        return this.copy(column = column.left() ?: return null, row = row.up() ?: return null)
    }
}
