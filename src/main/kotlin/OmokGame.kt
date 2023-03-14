class OmokGame(
    private var blackStones: Stones = BlackStones(),
    private var whiteStones: Stones = WhiteStones()
) {
    fun runGame(getPoint: (Stones) -> Point, boardState: (Stones, Stones) -> Unit): Color {
        while (true) {
            boardState(blackStones, whiteStones)
            if (blackTurn(getPoint)) {
                boardState(blackStones, whiteStones)
                return Color.BLACK
            }
            boardState(blackStones, whiteStones)
            if (whiteTurn(getPoint)) {
                boardState(blackStones, whiteStones)
                return Color.WHITE
            }
        }
    }

    fun blackTurn(getPoint: (Stones) -> Point): Boolean {
        while (true) {
            val point = getPoint(blackStones)
            if (blackStones.isPossiblePut(point) && whiteStones.isPossiblePut(point)) {
                blackStones = blackStones.putStone(Stone(point))
                if (blackStones.isWin) return true
                return false
            }
        }
    }

    fun whiteTurn(getPoint: (Stones) -> Point): Boolean {
        while (true) {
            val point = getPoint(whiteStones)
            if (blackStones.isPossiblePut(point) && whiteStones.isPossiblePut(point)) {
                whiteStones = whiteStones.putStone(Stone(point))
                if (whiteStones.isWin) return true
                return false
            }
        }
    }
}
