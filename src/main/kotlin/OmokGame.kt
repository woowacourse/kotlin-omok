// class OmokGame(
//     val getPoint: (placedStones) -> Point,
//     val checkBoardState: (placedStones, placedStones) -> Unit,
// ) {
//
//     fun runGame(
//         aBlackplacedStones: placedStones = BlackplacedStones(),
//         aWhiteplacedStones: placedStones = WhiteplacedStones()
//     ): Color {
//         var blackplacedStones: placedStones = aBlackplacedStones
//         var whiteplacedStones: placedStones = aWhiteplacedStones
//
//         while (true) {
//             checkBoardState(blackplacedStones, whiteplacedStones)
//             blackplacedStones = blackplacedStones.eachTurn(whiteplacedStones, getPoint)
//             blackplacedStones.isFinished(whiteplacedStones) ?: return blackplacedStones.getColor()
//             checkBoardState(blackplacedStones, whiteplacedStones)
//             whiteplacedStones = whiteplacedStones.eachTurn(blackplacedStones, getPoint)
//             whiteplacedStones.isFinished(blackplacedStones) ?: return whiteplacedStones.getColor()
//         }
//     }
//
//     private fun placedStones.isFinished(otherplacedStones: placedStones): Color? {
//         if (this.isWin) {
//             checkBoardState(this, otherplacedStones)
//             return null
//         }
//         return this.getColor()
//     }
// }
