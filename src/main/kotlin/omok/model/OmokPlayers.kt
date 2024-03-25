package omok.model

class OmokPlayers(val blackStonePlayer: Player, val whiteStonePlayer: Player) {
    fun firstOrderPlayer() = blackStonePlayer

    fun next(recentPlayer: Player): Player {
        return if (recentPlayer == blackStonePlayer) whiteStonePlayer else blackStonePlayer
    }

    fun winningFinishType(winningPlayer: Player): FinishType {
        return if (winningPlayer == blackStonePlayer) FinishType.BLACK_PLAYER_WIN else FinishType.WHITE_PLAYER_WIN
    }
}
