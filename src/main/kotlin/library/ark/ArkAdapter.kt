//package domain.library.ark
//
//import domain.CoordinateState
//import domain.Position
//import domain.domain.GameRule
//
//class ArkAdapter : GameRule {
//    override fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean =
//        ThreeThreeRule.validate(
//            toArkBoard(board),
//            position.coordinateX to position.coordinateY,
//        )
//
//    override fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean =
//        FourFourRule.validate(
//            toArkBoard(board),
//            position.coordinateX to position.coordinateY,
//        )
//
//    private fun convertCoordinateStateToArkNumber(coordinateState: CoordinateState): Int {
//        return when (coordinateState) {
//            CoordinateState.BLACK -> 1
//            CoordinateState.WHITE -> 2
//            CoordinateState.EMPTY -> 0
//        }
//    }
//
//    private fun toArkBoard(board: List<List<CoordinateState>>) =
//        board.map { it.map { coordinateState -> convertCoordinateStateToArkNumber(coordinateState) } }
//}
