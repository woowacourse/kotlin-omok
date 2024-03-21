package omok.model

/*sealed class Color {
    object BLACK : Color()

    object WHITE : Color()
}*/

enum class Color {
    BLACK, WHITE
}

fun Color.change(): Color {
    return if(this == Color.BLACK) Color.WHITE
    else Color.BLACK
}
