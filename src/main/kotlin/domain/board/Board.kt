package domain.board

class Board {

    companion object {
        val POSITIONS = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
