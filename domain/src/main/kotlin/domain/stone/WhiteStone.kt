package domain.stone

class WhiteStone(x: Int, y: Int) : Stone(x, y) {
    constructor(point: Pair<Int, Int>) : this(point.first, point.second)
}