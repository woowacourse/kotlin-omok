package omok.turn

class WhiteTurn() : Turn {
    override fun proceed(): Turn {
        return BlackTurn()
    }
}
