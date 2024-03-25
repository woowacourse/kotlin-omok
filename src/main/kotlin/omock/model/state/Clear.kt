package omock.model.state

class Clear(private val stone: Stone) : UnPlaced(stone) {
    override fun getNumber(): Int = CLEAR_NUMBER
}
