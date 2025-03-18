package omok.model

enum class IntersectionState {
    EMPTY,
    BLACK,
    WHITE,
    ;

    fun reverse(): IntersectionState {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
            EMPTY -> EMPTY
        }
    }
}
