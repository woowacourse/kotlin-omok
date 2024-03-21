package omok.model

import library.OmokRule

class RuleAdapter(stones: Stones, currentColor: Color) : Rule {
    private val omokRule =
        OmokRule(
            generateCustomBoard(stones),
            colorToInt(currentColor),
            getOtherColorToInt(currentColor),
            Board.getSize(),
        )

    override fun checkThreeThree(stone: Stone): Boolean {
        return omokRule.checkThreeThree(stone.point.col, stone.point.row)
    }

    override fun checkFourFour(stone: Stone): Boolean {
        return omokRule.countFourFour(stone.point.col, stone.point.row)
    }

    override fun checkMoreThanFive(stone: Stone): Boolean {
        return omokRule.checkMoreThanFive(stone.point.col, stone.point.row)
    }

    override fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val libraryBoard =
            List(Board.getSize()) {
                MutableList(Board.getSize()) { 0 }
            }
        stones.stones.forEach {
            if (it.color == Color.BLACK) {
                libraryBoard[it.point.col][it.point.row] = 1
            } else {
                libraryBoard[it.point.col][it.point.row] = 2
            }
        }
        return libraryBoard
    }

    private fun colorToInt(color: Color): Int {
        if (color == Color.BLACK) return 1
        return 2
    }

    private fun getOtherColorToInt(color: Color): Int {
        if (color == Color.BLACK) return 2
        return 1
    }
}
