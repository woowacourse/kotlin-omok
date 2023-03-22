package domain.rule

data class OmokPoint(val x: XCoordinate, val y: YCoordinate) {
    constructor(x: Char, y: Int) : this(XCoordinate(x), YCoordinate(y))
}
