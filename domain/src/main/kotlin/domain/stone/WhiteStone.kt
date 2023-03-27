package domain.stone

class WhiteStone(point: Point) : Stone(point) {
    constructor(point: Pair<Int, Int>) : this(Point(point.first, point.second))
    constructor(x: Int, y: Int) : this(Point(x, y))
}