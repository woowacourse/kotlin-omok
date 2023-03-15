interface Board {
    val isFinished: Boolean
    val winningColor: Color
    fun isPossiblePut(position: Position): Boolean
    fun getLatestStone(): Stone?
    fun getStones(): List<Stone>
    fun putStone(stone: Stone): Board
}
