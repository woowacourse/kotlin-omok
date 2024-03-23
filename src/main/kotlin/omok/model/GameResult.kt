package omok.model

enum class GameResult(val label: String, val color: Color?) {
    WINNER_BLACK("흑돌", Color.BLACK),
    WINNER_WHITE("백돌", Color.WHITE),
    DRAW("무승부", null),
}
