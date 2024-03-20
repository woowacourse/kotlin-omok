package omok.model

//TODO 네이밍 변경
interface Rule {
    fun canPut(stone: OmokStone, board: Board): Boolean
}

class GeneralRule : Rule {
    override fun canPut(stone: OmokStone, board: Board): Boolean {
        val isEmptyPosition = board.isEmptyPosition(stone)
        val isInRange = board.isInRange(stone)
        return isEmptyPosition && isInRange
    }
}
