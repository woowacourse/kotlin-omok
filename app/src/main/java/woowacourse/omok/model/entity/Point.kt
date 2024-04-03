package woowacourse.omok.model.entity

data class Point(val x: Int, val y: Int) {
    companion object {
        fun findPoint(index: Int): Point {
            val x = index % 15 + 1
            val y = 15 - (index / 15)

            return Point(x, y)
        }
    }
}
