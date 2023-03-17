package domain.stone

data class Position(val column: Column, val row: Row) {
    constructor(x: Int, y: Int) : this(Column.valueOf(x), Row.valueOf(y))

    fun getNorth(): Position? {
        val northRow = row.up() ?: return null
        return Position(column, northRow)
    }

    fun getNorthEast(): Position? {
        val eastColumn = column.right() ?: return null
        val northRow = row.up() ?: return null
        return Position(eastColumn, northRow)
    }

    fun getEast(): Position? {
        val eastColumn = column.right() ?: return null
        return Position(eastColumn, row)
    }

    fun getSouthEast(): Position? {
        val eastColumn = column.right() ?: return null
        val southRow = row.down() ?: return null
        return Position(eastColumn, southRow)
    }

    fun getSouth(): Position? {
        val southRow = row.down() ?: return null
        return Position(column, southRow)
    }

    fun getSouthWest(): Position? {
        val westColumn = column.left() ?: return null
        val southRow = row.down() ?: return null
        return Position(westColumn, southRow)
    }

    fun getWest(): Position? {
        val westColumn = column.left() ?: return null
        return Position(westColumn, row)
    }

    fun getNorthWest(): Position? {
        val westColumn = column.left() ?: return null
        val northRow = row.up() ?: return null
        return Position(westColumn, northRow)
    }
}
