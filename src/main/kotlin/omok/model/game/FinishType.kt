package omok.model.game

import omok.model.board.Stone

enum class FinishType(val stone: Stone) {
    BLACK_PLAYER_WIN(Stone.BLACK),
    WHITE_PLAYER_WIN(Stone.WHITE),
    DRAW(Stone.NONE),
    NOT_FINISH(Stone.NONE),
    ;

    fun isFinish() = this != NOT_FINISH

    companion object {
        fun winning(stone: Stone?) = if (stone == Stone.BLACK) BLACK_PLAYER_WIN else WHITE_PLAYER_WIN
    }
}
