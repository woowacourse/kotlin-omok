package domain.stone

class BlackStone : Stone {
    constructor(point: Point) : super(point)
    constructor(x: Int, y: Int) : super(Point(x, y))
}