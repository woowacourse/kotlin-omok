package domain.stone

class WhiteStone : Stone {
    constructor(point: Point) : super(point)
    constructor(x: Int, y: Int) : super(Point(x, y))
}