package omok.model

class Game {
    private var _lastStone = Stone(Point(1, 1), StoneColor.WHITE)
    val lastStone get() = _lastStone.copy()

    fun play(stone: Stone): GameState {
        val currentColor: StoneColor = _lastStone.color.reverse()
        val gameState: GameState = GameState.PLAYING // TODO 게임 상태 설정
        _lastStone = Stone(stone.point, currentColor)
        return gameState
    }
}
