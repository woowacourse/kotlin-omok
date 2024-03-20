package omok.model

class Stone(val point: Point) {
    constructor(row: Int, col: Int) :
        this(Point(row, col))
}

//
