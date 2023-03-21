package omok.domain

data class OmokPoint(val x: XCoordinate, val y: YCoordinate) {
    constructor(x: Char, y: Int) : this(XCoordinate(x), YCoordinate(y))
    constructor(x: Int, y: Int) : this(XCoordinate(x), YCoordinate(y))

    companion object {
        fun createLinesPoint(xSize: Int, ySize: Int): List<OmokPoint> =
            XCoordinate.createXLine(xSize).flatMap { x ->
                YCoordinate.createYLine(ySize).map { y ->
                    OmokPoint(x, y)
                }
            }
    }
}
