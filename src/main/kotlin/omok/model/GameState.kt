package omok.model

sealed class GameState(val board: Board) {
    abstract fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    abstract fun getNextTurn(boardResult: BoardResult): GameState

    abstract fun getCurrentTurn(): GameState

    abstract fun getCurrentStoneType(): PositionType

    abstract fun setupRule()

    sealed class Running(board: Board) : GameState(board) {

        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            onShow(board)
            onTurn(this)

            val position = onRead()
            val boardResult = board.placeStone(position, getCurrentStoneType())

            return getNextTurn(boardResult)
        }

        override fun getNextTurn(boardResult: BoardResult): GameState {
            return Finish(board)
        }

        sealed class BlackTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }
            final override fun setupRule() {
                board.setupOmokRule(PositionType.BLACK_STONE)
            }
            override fun getNextTurn(boardResult: BoardResult): GameState {
                return when(boardResult) {
                    BoardResult.Block -> {
                        Block(board)
                    }
                    BoardResult.Duplicate -> {
                        Duplicate(board)
                    }
                    BoardResult.Done -> {
                        WhiteTurn.Start(board)
                    }
                    else -> {
                        super.getNextTurn(boardResult)
                    }
                }
            }

            override fun getCurrentStoneType(): PositionType = PositionType.BLACK_STONE

            class Start(board: Board): BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Start(board)
            }

            class Block(board: Board): BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Block(board)
            }

            class Duplicate(board: Board): BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Duplicate(board)
            }
        }

        sealed class WhiteTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }

            final override fun setupRule() {
                board.setupOmokRule(PositionType.WHITE_STONE)
            }

            override fun getNextTurn(boardResult: BoardResult): GameState {
                return when(boardResult) {
                    BoardResult.Done -> {
                        println("여기로와야하는데")
                        BlackTurn.Start(board)
                    }
                    BoardResult.Duplicate -> {
                        Duplicate(board)
                    }
                    else -> {
                        super.getNextTurn(boardResult)
                    }
                }
            }

            override fun getCurrentStoneType(): PositionType = PositionType.WHITE_STONE

            class Start(board: Board): WhiteTurn(board) {
                override fun getCurrentTurn(): GameState = Start(board)
            }

            class Duplicate(board: Board): WhiteTurn(board) {
                override fun getCurrentTurn(): GameState = Duplicate(board)
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun getCurrentStoneType(): PositionType {
            return PositionType.EMPTY
        }

        override fun setupRule() {
        }

        override fun getCurrentTurn(): GameState {
            return this
        }

        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            return this
        }
        override fun getNextTurn(boardResult: BoardResult): GameState {
            return this
        }
    }
}
