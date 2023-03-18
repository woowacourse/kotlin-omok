package view

enum class BoardComposition(val value: String) {

    LEFT_TOP("┌"),
    RIGHT_TOP("┐"),
    LEFT_CONNECTION_HORIZONTAL("├"),
    RIGHT_CONNECTION_HORIZONTAL("┤"),
    CONNECTION_HORIZONTAL_DOWN("┬"),
    CONNECTION_HORIZONTAL_UP("┴"),
    CONNECTION_HORIZONTAL_VERTICAL("┼"),
    LEFT_BOTTOM("└"),
    RIGHT_BOTTOM("┘"),
    CONNECTING_HORIZONTAL("─");

    companion object {
        fun valueOf(x: Int, y: Int): BoardComposition {
            val END = 14
            return when {
                x == 0 && y == END -> LEFT_TOP
                x == END && y == END -> RIGHT_TOP
                x == 0 && y == 0 -> LEFT_BOTTOM
                x == END && y == 0 -> RIGHT_BOTTOM
                x == 0 -> LEFT_CONNECTION_HORIZONTAL
                x == END -> RIGHT_CONNECTION_HORIZONTAL
                y == END -> CONNECTION_HORIZONTAL_DOWN
                y == 0 -> CONNECTION_HORIZONTAL_UP
                else -> CONNECTION_HORIZONTAL_VERTICAL
            }
        }
    }
}
