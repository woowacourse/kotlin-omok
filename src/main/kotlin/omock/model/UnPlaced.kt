package omock.model

abstract class UnPlaced(private val stone: Stone) : StoneState {
    override fun put(player: Player): StoneState {
        return when (player) {
            is BlackPlayer -> Black(stone)
            is WhitePlayer -> White(stone)
        }
    }
}
