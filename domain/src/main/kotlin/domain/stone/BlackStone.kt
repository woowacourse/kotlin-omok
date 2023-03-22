package domain.stone

class BlackStone(x: Int, y: Int) : Stone(x, y) {
    constructor(point: Pair<Int, Int>) : this(point.first, point.second)
}