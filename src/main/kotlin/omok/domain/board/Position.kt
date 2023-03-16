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
        val northRow = row.up()
        if (northRow != null) return Position(column, northRow)
        return null
    }

    fun getNorthEast(): Position? {
        val eastColumn = column.right()
        val northRow = row.up()
        if (eastColumn != null && northRow != null) return Position(eastColumn, northRow)
        return null
    }

    fun getEast(): Position? {
        val eastColumn = column.right()
        if (eastColumn != null) return Position(eastColumn, row)
        return null
    }

    fun getSouthEast(): Position? {
        val eastColumn = column.right()
        val southRow = row.down()
        if (eastColumn != null && southRow != null) return Position(eastColumn, southRow)
        return null
    }

    fun getSouth(): Position? {
        val southRow = row.down()
        if (southRow != null) return Position(column, southRow)
        return null
    }

    fun getSouthWest(): Position? {
        val westColumn = column.left()
        val southRow = row.down()
        if (westColumn != null && southRow != null) return Position(westColumn, southRow)
        return null
    }

    fun getWest(): Position? {
        val westColumn = column.left()
        if (westColumn != null) return Position(westColumn, row)
        return null
    }

    fun getNorthWest(): Position? {
        val westColumn = column.left()
        val northRow = row.up()
        if (westColumn != null && northRow != null) return Position(westColumn, northRow)
        return null
    }
}
