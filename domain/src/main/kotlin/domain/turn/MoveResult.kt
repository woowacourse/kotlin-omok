package domain.turn

sealed class MoveResult {
    class Success : MoveResult()
    class Fail : MoveResult()
}
