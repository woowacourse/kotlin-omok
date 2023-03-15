interface Board {
    val isFinished: Boolean
    val isWin: Color
    fun isPossiblePut(point: Point): Boolean
    fun getLatestPoint(color: Color): Point?
    fun getPlacedStones(): List<Stone>
    fun putStone(stone: Stone): Board
}
