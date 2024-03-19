package omock.model

abstract class UnPlaced() : StoneState {
    override fun put(player: Player): StoneState {
        return when (player) {
            is BlackPlayer -> Black()
            is WhitePlayer -> White()
        }
    }

}
