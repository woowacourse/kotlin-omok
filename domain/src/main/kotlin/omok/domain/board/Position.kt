package omok.domain.board

import omok.domain.board.Column.Companion.toColumn
import omok.domain.board.Row.Companion.toRow

data class Position(val column: Column, val row: Row) {

    constructor(position: Pair<Int, Int>) : this(toColumn(position.first), toRow(position.second))

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
