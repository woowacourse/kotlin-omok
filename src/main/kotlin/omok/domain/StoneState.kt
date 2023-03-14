package omok.domain

enum class StoneState(val korean: String) {
    BLACK("흑"), WHITE("백"), EMPTY("");

    fun next() = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
        else -> throw IllegalStateException()
    }
}
