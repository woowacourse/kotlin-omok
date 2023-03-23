package omok

data class OmokPoint(val x: Int, val y: Int) {
    companion object {
        private const val CREATE_POINT_STANDARD_X = 1
        private const val CREATE_POINT_STANDARD_Y = 1
        fun createLinesPoint(xSize: Int, ySize: Int): List<OmokPoint> =
            (CREATE_POINT_STANDARD_X..xSize).flatMap { x ->
                (CREATE_POINT_STANDARD_Y..ySize).map { y ->
                    OmokPoint(x, y)
                }
            }

        fun from(index: Int): OmokPoint = OmokPoint(index % 15 + 1, index / 15 + 1)
    }
}
