package omok.domain.board

data class Point(
    val x: OmokColumn,
    val y: OmokRow,
    val stoneStatus: StoneStatus,
)
