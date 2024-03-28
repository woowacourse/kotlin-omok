package omok.model.state

class Black(private val stone: Stone) : Placed(stone) {
    override fun getNumber(): Int = BLACK_NUMBER
}
