package woowacourse.omok.model

enum class Vector(val x: Int, val y: Int) {
    UpwardRight(1, 1),
    DownwardRight(1, -1),
    Horizontal(0, 1),
    Vertical(1, 0),
}
