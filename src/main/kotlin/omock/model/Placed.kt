package omock.model

abstract class Placed(private val stone: Stone) : StoneState {
    override fun put(player: Player): StoneState {
        throw IllegalArgumentException()
    }
}
