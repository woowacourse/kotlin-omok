package omock.model

abstract class Placed() : StoneState {
    override fun put(player: Player): StoneState {
        throw IllegalArgumentException()
    }
}
