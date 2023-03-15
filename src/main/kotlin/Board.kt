interface Board {
    val isFinished: Boolean
    val isWin: Color
    fun isPossiblePut(point: Point): Boolean
    fun getLatestPoint(color: Color): Point?
    fun getStones(): List<Stone>
    fun putStone(stone: Stone): Board
}
