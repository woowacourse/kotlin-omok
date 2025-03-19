package omok.domain.board

enum class StoneStatus {
    BLACK,
    WHITE,
    EMPTY,
    ;

    companion object {
        fun getAllStoneStatus(): List<StoneStatus> {
            return listOf(BLACK, WHITE)
        }
    }
}
