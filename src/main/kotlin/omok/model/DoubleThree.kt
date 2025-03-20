// package omok.model
//
// import omok.model.board.OmokBoard
// import omok.model.board.Position
// import omok.model.board.X
// import omok.model.stone.StoneState
//
// class DoubleThree : Rule {
//    override fun findOmok(
//        position: Position,
//        stone: StoneState,
//        board: OmokBoard,
//    ): Boolean = true
//
//    fun findDoubleThree(
//        position: Position,
//        stone: StoneState,
//        board: OmokBoard,
//    ): Boolean {
//        val x = position.x
//        val y = position.y
//
//        // 가로
//        var newX = x.point - 3
//        var countX = 0
//        var empty = 1
//        repeat(7) {
//            if (newX in 1..15) {
//                if (board.boardState(Position(X(newX), y)) == stone) {
//                    countX++
//                    if (countX == 5) return true
//                } else if (board.boardState(Position(X(newX), y)) != StoneState.NONE && empty == 1) {
//                    continue
//                } else {
//                    countX = 0
//                }
//            }
//            newX++
//        }
//    }
// }
