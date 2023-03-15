abstract class BasedBoard(protected val placedStones: List<Stone>) : Board {

    override fun getStones(): List<Stone> = placedStones.toList()

    override fun getLatestStone(): Stone? {
        return placedStones.last()
    }
}
