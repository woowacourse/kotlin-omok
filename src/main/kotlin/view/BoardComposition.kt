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

        private val END = 15

        fun valueOf(x: Int, y: Int): BoardComposition {
            return when {
                x == 1 && y == END -> LEFT_TOP
                x == END && y == END -> RIGHT_TOP
                x == 1 && y == 1 -> LEFT_BOTTOM
                x == END && y == 1 -> RIGHT_BOTTOM
                x == 1 -> LEFT_CONNECTION_HORIZONTAL
                x == END -> RIGHT_CONNECTION_HORIZONTAL
                y == END -> CONNECTION_HORIZONTAL_DOWN
                y == 1 -> CONNECTION_HORIZONTAL_UP
                else -> CONNECTION_HORIZONTAL_VERTICAL
            }
        }
    }
}
