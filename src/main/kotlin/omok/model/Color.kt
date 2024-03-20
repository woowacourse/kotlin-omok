package omok.model

enum class Color(val label: String) {
    BLACK("흑"),
    WHITE("백"),
    ;

    companion object {
        fun getReversedColor(color: Color): Color {
            return when (color) {
                BLACK -> WHITE
                WHITE -> BLACK
            }
        }
    }
}
