package woowacourse.omok.model.rule

enum class Direction(
    val dx: Int,
    val dy: Int,
) {
    LEFT(-1, 0),
    RIGHT(1, 0), // 좌, 우
    UP(0, -1),
    DOWN(0, 1), // 상, 하
    LEFT_DOWN(-1, 1),
    RIGHT_UP(1, -1), // 좌하 , 우상 대각선
    RIGHT_DOWN(1, 1),
    LEFT_UP(-1, -1), // 우하 , 좌상
    ;

    companion object {
        fun lineDirections(): List<Pair<Direction, Direction>> =
            listOf(
                LEFT to RIGHT,
                UP to DOWN,
                LEFT_DOWN to RIGHT_UP,
                RIGHT_DOWN to LEFT_UP,
            )
    }
}
