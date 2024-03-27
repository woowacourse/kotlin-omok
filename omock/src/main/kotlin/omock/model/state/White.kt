package omock.model.state

class White(private val stone: Stone) : Placed(stone) {
    override fun getNumber(): Int = WHITE_NUMBER
}
