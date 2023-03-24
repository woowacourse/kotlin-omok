// package domain.rule
//
// import domain.stone.Stone
// import domain.stone.Stones
// import rule.ArkRenjuRule
//
// class RenjuRuleAdapter(private val arkRule: ArkRenjuRule) : OmokRule {
//     override fun countOpenThrees(blackStones: Stones, whiteStones: Stones, stone: Stone): Int {
//         println(arkRule.countOpenThrees(stone.position.row - 1, stone.position.col - 1))
//         return arkRule.countOpenThrees(stone.position.row - 1, stone.position.col - 1)
//     }
//
//     override fun countOpenFours(blackStones: Stones, whiteStones: Stones, stone: Stone): Int {
//         println(arkRule.countOpenFours(stone.position.row - 1, stone.position.col - 1))
//         return arkRule.countOpenFours(stone.position.row - 1, stone.position.col - 1)
//     }
//
//     override fun validateWhiteWin(blackStones: Stones, whiteStones: Stones, stone: Stone): Boolean {
//         return arkRule.validateWhiteWin(stone.position.row - 1, stone.position.col - 1)
//     }
//
//     override fun validateBlackWin(blackStones: Stones, whiteStones: Stones, stone: Stone): Boolean {
//         return arkRule.validateBlackWin(stone.position.row - 1, stone.position.col - 1)
//     }
// }
