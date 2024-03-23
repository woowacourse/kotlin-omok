package omok.model

interface OmokTurnAction {
    fun nextStonePosition(
        nowOrderStone: Stone,
        recentPosition: Position?,
    ): Position

    fun onStonePlace(board: Board)
}
