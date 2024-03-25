package omok.model

enum class FinishType(val stone: Stone) {
    BLACK_PLAYER_WIN(Stone.BLACK),
    WHITE_PLAYER_WIN(Stone.WHITE),
    DRAW(Stone.NONE),
}
