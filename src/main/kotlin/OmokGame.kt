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
            if (blackStones.isWin) {
                checkBoardState(blackStones, whiteStones)
                // TODO: 굳이 COLOR를 반환해야할까?
                return blackStones.getColor()
            }
            checkBoardState(blackStones, whiteStones)
            whiteStones = whiteStones.eachTurn(blackStones, getPoint)
            if (blackStones.isWin) {
                checkBoardState(blackStones, whiteStones)
                // TODO: 굳이 COLOR를 반환해야할까?
                return whiteStones.getColor()
            }
        }
    }
}
