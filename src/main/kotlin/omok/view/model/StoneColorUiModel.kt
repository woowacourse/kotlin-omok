package omok.view.model

enum class StoneColorUiModel(val symbol: String = "") {
    BLACK("●"),

    WHITE("○"),
    ;

    fun format() =
        when (this) {
            BLACK -> "흑"
            WHITE -> "백"
        }
}
