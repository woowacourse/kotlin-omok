package omok.turn

class BlackTurn() : Turn {
    override fun proceed(): Turn {
        return WhiteTurn()
    }
}
