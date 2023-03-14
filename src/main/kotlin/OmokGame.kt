class OmokGame(
    val getPoint: (Stones) -> Point,
    val checkBoardState: (Stones, Stones) -> Unit,
) {

    fun runGame(
        aBlackStones: Stones = BlackStones(),
        aWhiteStones: Stones = WhiteStones()
    ): Color {
        var blackStones: Stones = aBlackStones
        var whiteStones: Stones = aWhiteStones

        while (true) {
            checkBoardState(blackStones, whiteStones)
            blackStones = blackStones.eachTurn(whiteStones, getPoint)
            blackStones.isFinished(whiteStones) ?: return blackStones.getColor()
            checkBoardState(blackStones, whiteStones)
            whiteStones = whiteStones.eachTurn(blackStones, getPoint)
            whiteStones.isFinished(blackStones) ?: return whiteStones.getColor()
        }
    }

    private fun Stones.isFinished(otherStones: Stones): Color? {
        if (this.isWin) {
            checkBoardState(this, otherStones)
            return null
        }
        return this.getColor()
    }
}
