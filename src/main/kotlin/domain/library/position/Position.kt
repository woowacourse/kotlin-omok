package domain.library.position

data class Position(val column: Column, val row: Row) {
    constructor(x: Int, y: Int) : this(Column.valueOf(x), Row.valueOf(y))

    // TODO: (방향을 결정하는 다른 함수도 동일하게 고려할 부분입니다.)
    // north 를 구하는 로직이 바뀌면, 현재는 getNorth, getNorthEast 모두 변경이 발생할 것 같은데요.
    // 로직을 재활용해보는 방향으로 리펙토링 해볼 수 있을까요?
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
