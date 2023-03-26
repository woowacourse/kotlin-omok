package domain.point

data class Points(private val points: List<Point> = emptyList()) {
    val last: Point?
        get() = points.lastOrNull()

    constructor(vararg points: Point) : this(points.toList())

    init {
        require(points.distinct().size == points.size) { DUPLICATED_ERROR_MESSAGE }
    }

    fun getAll(): List<Point> = points.map { Point(it.row, it.col) }

    fun add(newStone: Point): Points {
        return copy(points = points + newStone)
    }

    fun hasStone(stone: Point): Boolean = points.contains(stone)

    companion object {
        private const val DUPLICATED_ERROR_MESSAGE = "중복되는 위치의 오목알을 가질 수 없습니다."
    }
}
