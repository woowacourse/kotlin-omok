package omok.model

sealed class GameState(val board: Board) {
    abstract fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        open fun checkOmok(lastPosition: Pair<Int, Int>): Boolean {
            val lastStoneType = board.layout[lastPosition.first][lastPosition.second]

            for (dir in directions) {
                var count = 1 // 마지막으로 놓인 돌 포함
                // 해당 방향으로 연속된 돌 확인
                count += countStones(lastPosition, dir, lastStoneType)
                // 반대 방향으로 연속된 돌 확인
                count += countStones(lastPosition, Pair(-dir.first, -dir.second), lastStoneType)

                if (count >= 5) return true // 오목이면 true 반환
            }

            return false // 오목이 아니면 false 반환
        }

        open fun countStones(
            start: Pair<Int, Int>,
            direction: Pair<Int, Int>,
            stoneType: StoneType,
        ): Int {
            var count = 0
            var pos = Pair(start.first + direction.first, start.second + direction.second)

            while (pos.first in 0
                until BOARD_SIZE && pos.second in 0
                until BOARD_SIZE && board.layout[pos.first][pos.second] == stoneType
            ) {
                count++
                pos = Pair(pos.first + direction.first, pos.second + direction.second)
            }

            return count
        }

        companion object {
            private const val BOARD_SIZE = 15
            private val directions =
                arrayOf(
                    Pair(-1, 0),
                    Pair(1, 0),
                    Pair(0, -1),
                    Pair(0, 1),
                    Pair(-1, -1),
                    Pair(-1, 1),
                    Pair(1, -1),
                    Pair(1, 1),
                )
        }

        class BlackTurn(board: Board) : Running(board) {
            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onTurn(this)
                val position = onRead()

                board.placeStone(position, StoneType.BLACK_STONE)
                onShow(board)

                if (checkOmok(Pair(position.coordinate.x, position.coordinate.y))) {
                    return Finish(board)
                }
                return WhiteTurn(board)
            }
        }

        class WhiteTurn(board: Board) : Running(board) {
            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onTurn(this)
                val position = onRead()

                board.placeStone(position, StoneType.WHITE_STONE)
                onShow(board)

                if (checkOmok(Pair(position.coordinate.x, position.coordinate.y))) {
                    return Finish(board)
                }
                return BlackTurn(board)
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            TODO("Not yet implemented")
        }
    }
}
