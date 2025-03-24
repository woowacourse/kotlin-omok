package omok.domain.point

sealed class Point2 {
    abstract val x: Int
    abstract val y: Int

    abstract fun toggle(position: String): Point2
}
