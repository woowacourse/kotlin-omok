package omok.domain

data class OmokPoint(val x: XCoordinate, val y: YCoordinate) {
    constructor(x: Char, y: Int) : this(XCoordinate(x), YCoordinate(y))
    companion object {
        fun all(): List<OmokPoint> = XCoordinate.all().flatMap { x -> YCoordinate.all().map { y -> OmokPoint(x, y) } }
    }
}
