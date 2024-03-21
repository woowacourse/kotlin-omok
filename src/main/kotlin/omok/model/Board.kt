package omok.model

class Board {
    var layout: Array<Array<PositionType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { PositionType.EMPTY } }
    var lastPosition: Position? = null

    private fun copy(): Board {
        val newBoard = Board()
        newBoard.layout =
            Array(layout.size) { i ->
                layout[i].copyOf()
            }
        newBoard.lastPosition = lastPosition?.copy()
        return newBoard
    }

    fun removeBlock() {
        layout.forEach { row ->
            row.forEachIndexed { index, stoneType ->
                if (stoneType == PositionType.BLOCK) {
                    row[index] = PositionType.EMPTY
                }
            }
        }
    }

    fun placeStone(
        position: Position,
        positionType: PositionType,
    ) {
        if (layout[position.coordinate.x][position.coordinate.y] == PositionType.EMPTY) {
            layout[position.coordinate.x][position.coordinate.y] = positionType
            lastPosition = position
        } else {
            throw IllegalArgumentException(ERROR_INVALID_POSITION)
        }
    }

    fun generateBlock(
        a: (Int, Int) -> Boolean,
        b: (Int, Int) -> Boolean,
        c: (Int, Int) -> Boolean,
    ): Board {
        var newBoard = this.copy()
        val parList: MutableList<Pair<Int, Int>> = mutableListOf()

        runCatching {
            for (i in 0 until BOARD_SIZE) {
                for (j in 0 until BOARD_SIZE) {
                    if ((a(i, j) || b(i, j) || c(i, j)) && newBoard.layout[i][j] == PositionType.EMPTY) {
                        parList.add(Pair(i, j))
                    }
                }
            }
        }.onFailure {
            throw IllegalArgumentException(ERROR_BLOCK_STONE)
        }

        parList.forEach {
            newBoard.layout[it.first][it.second] = PositionType.BLOCK
        }

        return newBoard
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val ERROR_INVALID_POSITION = "돌을 놓을 수 없는 자리입니다."
        private const val ERROR_BLOCK_STONE = "BLOCK 생성을 실패했습니다."
    }
}
