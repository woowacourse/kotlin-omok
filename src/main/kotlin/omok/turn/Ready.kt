package omok.turn

class Ready : Turn {
    override fun proceed(): Turn {
        return BlackTurn()
    }
}
