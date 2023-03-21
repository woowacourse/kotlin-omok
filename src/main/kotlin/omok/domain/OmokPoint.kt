package omok.domain

data class OmokPoint(val x: XCoordinate, val y: YCoordinate) {
    constructor(x: Int, y: Int) : this(XCoordinate(x), YCoordinate(y))

    init {
        require(x.value in BOARD_MIN_X_SIZE..BOARD_MAX_X_SIZE) { ERROR_OVER_X_RANGE }
        require(y.value in BOARD_MIN_Y_SIZE..BOARD_MAX_Y_SIZE) { ERROR_OVER_Y_RANGE }
    }

    companion object {
        private const val BOARD_MIN_X_SIZE = 1
        private const val BOARD_MIN_Y_SIZE = 1
        private const val BOARD_MAX_X_SIZE = 15
        private const val BOARD_MAX_Y_SIZE = 15
        private const val ERROR_OVER_X_RANGE = "X 범위를 벗어납니다."
        private const val ERROR_OVER_Y_RANGE = "Y 범위를 벗어납니다."
        fun createLinesPoint(xSize: Int, ySize: Int): List<OmokPoint> =
            XCoordinate.createXLine(xSize).flatMap { x ->
                YCoordinate.createYLine(ySize).map { y ->
                    OmokPoint(x, y)
                }
            }
    }
}
