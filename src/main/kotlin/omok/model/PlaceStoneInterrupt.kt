import omok.model.Board

sealed class PlaceStoneInterrupt(val errorMessage: String) {
    class StoneOutOfBoard() : PlaceStoneInterrupt(STONE_OUT_OF_BOARD_MESSAGE)

    class StoneAlreadyExists() : PlaceStoneInterrupt(STONE_ALREADY_EXISTS_MESSAGE)

    class StoneViolatedRules() : PlaceStoneInterrupt(STONE_VIOLATED_RULES)

    class GameFinished(val board: Board) : PlaceStoneInterrupt(GAME_FINISHED)

    companion object {
        private const val STONE_OUT_OF_BOARD_MESSAGE = "돌을 보드 밖에 두었습니다."
        private const val STONE_VIOLATED_RULES = "룰을 어기셨네요."
        private const val STONE_ALREADY_EXISTS_MESSAGE = "그 위치에는 이미 돌이 있습니다."
        private const val GAME_FINISHED = "게임이 끝났습니다."
    }
}
