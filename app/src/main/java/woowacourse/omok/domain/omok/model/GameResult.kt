package woowacourse.omok.domain.omok.model

enum class GameResult(val label: String, val color: Color) {
    WINNER_BLACK("흑돌", Color.BLACK),
    WINNER_WHITE("백돌", Color.WHITE),
    PROCEEDING("진행중", Color.NONE),
    DRAW("무승부", Color.NONE),
}
