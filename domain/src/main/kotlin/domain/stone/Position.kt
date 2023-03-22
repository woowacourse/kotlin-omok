package domain.stone

data class Position(val column: Column, val row: Row) {
    constructor(x: Int, y: Int) : this(Column.valueOf(x - 1), Row.valueOf(y - 1))

    fun getNorth(): Position? {
        return this.copy(row = row.up() ?: return null)
    }

    fun getNorthEast(): Position? {
        return getNorth()?.getEast()
    }

    fun getEast(): Position? {
        return this.copy(column = column.right() ?: return null)
    }

    fun getSouthEast(): Position? {
        return getSouth()?.getEast()
    }

    fun getSouth(): Position? {
        return this.copy(row = row.down() ?: return null)
    }

    fun getSouthWest(): Position? {
        return getSouth()?.getWest()
    }

    fun getWest(): Position? {
        return this.copy(column = column.left() ?: return null)
    }

    fun getNorthWest(): Position? {
        return getNorth()?.getWest()
    }

    companion object {
        fun all() = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
