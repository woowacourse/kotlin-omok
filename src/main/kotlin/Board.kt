interface Board {
    val isFinished: Boolean
    val winningColor: Color
    fun isPossiblePut(point: Position): Boolean
    fun getLatestStone(): Stone?
    fun getStones(): List<Stone>
    fun putStone(stone: Stone): Board
}
