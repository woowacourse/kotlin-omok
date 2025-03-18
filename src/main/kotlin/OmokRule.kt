interface OmokRule {
    fun canPlace(): PlaceResult

    fun isEnd(): GameResult
}
