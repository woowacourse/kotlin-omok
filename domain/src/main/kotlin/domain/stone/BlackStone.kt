package domain.stone

class BlackStone : Stone {
    constructor(point: Point) : super(point)
    constructor(x: Char, y: Int) : super(Point(x, y))
    constructor(xCoordinate: XCoordinate, yCoordinate: YCoordinate) : super(Point(xCoordinate.x, yCoordinate.y))
}