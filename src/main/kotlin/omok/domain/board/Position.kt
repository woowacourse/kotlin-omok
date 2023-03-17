package omok.domain.board

fun String?.toPosition(): Position {
    val columnText = this?.substring(0, 1)
    val rowText = this?.substring(1)
    val column = columnText.toColumn()
    val row = rowText.toRow()

    require(column != null && row != null) { "[ERROR] ${this ?: ""} 라는 위치는 존재하지 않습니다." }
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
