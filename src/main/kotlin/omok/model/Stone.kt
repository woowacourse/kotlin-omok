package omok.model

class Stone(val coordinate: Coordinate) {
    constructor(row: String, col: Int) :
        this(Coordinate(row, col))
}
