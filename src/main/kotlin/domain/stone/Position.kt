package domain.stone

data class Position(val column: Column, val row: Row) {
    constructor(x: Int, y: Int) : this(Column.valueOf(x), Row.valueOf(y))

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
