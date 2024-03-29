package woowacourse.omok

sealed class GameState(val board: Board) {
    abstract fun placeStone(coordinate: Coordinate): GameState

    abstract fun getNextTurn(placeResult: PlaceResult): GameState

    abstract fun getCurrentTurn(): GameState

    abstract fun getCurrentStoneType(): PositionType

    abstract fun setupRule()

    sealed class Running(board: Board) : GameState(board) {
        override fun placeStone(coordinate: Coordinate): GameState {
            val boardResult = board.placeStone(coordinate)
            return getNextTurn(boardResult)
        }

        override fun getNextTurn(placeResult: PlaceResult): GameState {
            return Finish(board)
        }

        sealed class BlackTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }

            final override fun setupRule() {
                board.setUpBoard(PositionType.BLACK_STONE)
            }

            override fun getNextTurn(placeResult: PlaceResult): GameState {
                return when (placeResult) {
                    PlaceResult.Block -> {
                        Block(board)
                    }
                    PlaceResult.Duplicate -> {
                        Duplicate(board)
                    }
                    PlaceResult.Done -> {
                        WhiteTurn.Start(board)
                    }
                    else -> {
                        super.getNextTurn(placeResult)
                    }
                }
            }

            override fun getCurrentStoneType(): PositionType = PositionType.BLACK_STONE

            class Start(board: Board) : BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Start(board)
            }

            class Block(board: Board) : BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Block(board)
            }

            class Duplicate(board: Board) : BlackTurn(board) {
                override fun getCurrentTurn(): GameState = Duplicate(board)
            }
        }

        sealed class WhiteTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }

            final override fun setupRule() {
                board.setUpBoard(PositionType.WHITE_STONE)
            }

            override fun getNextTurn(placeResult: PlaceResult): GameState {
                return when (placeResult) {
                    PlaceResult.Done -> {
                        BlackTurn.Start(board)
                    }
                    PlaceResult.Duplicate -> {
                        Duplicate(board)
                    }
                    else -> {
                        super.getNextTurn(placeResult)
                    }
                }
            }

            override fun getCurrentStoneType(): PositionType = PositionType.WHITE_STONE

            class Start(board: Board) : WhiteTurn(board) {
                override fun getCurrentTurn(): GameState = Start(board)
            }

            class Duplicate(board: Board) : WhiteTurn(board) {
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

        override fun placeStone(coordinate: Coordinate): GameState {
            return this
        }

        override fun getNextTurn(placeResult: PlaceResult): GameState {
            return this
        }
    }
}
