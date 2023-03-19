package domain.domain

import domain.library.OmokRule

class RuleAdapter(stones: Stones, currentColor: Color) : Rule {
    private val omokRule =
        OmokRule(
            generateCustomBoard(stones),
            colorToInt(currentColor),
            getOtherColorToInt(currentColor),
            Board2.getSize(),
        )

    override fun checkThreeThree(stone: Stone): Boolean {
        return omokRule.checkThreeThree(stone.position.x, stone.position.y)
    }

    override fun checkFourFour(stone: Stone): Boolean {
        return omokRule.countFourFour(stone.position.x, stone.position.y)
    }

    override fun checkBlackWin(stone: Stone): Boolean {
        if (stone.isBlack()) return omokRule.validateBlackWin(stone.position.x, stone.position.y)
        return false
    }

    override fun checkWhiteWin(stone: Stone): Boolean {
        if (stone.isWhite()) return omokRule.validateWhiteWin(stone.position.x, stone.position.y)
        return false
    }

    override fun checkMoreThanFive(stone: Stone): Boolean {
        return omokRule.checkMoreThanFive(stone.position.x, stone.position.y)
    }

    override fun checkInvalid(stone: Stone): Boolean {
        if (stone.isBlack()) return checkThreeThree(stone) || checkFourFour(stone) || checkMoreThanFive(stone)
        return false
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val libraryBoard = List(Board2.getSize()) {
            MutableList(Board2.getSize()) { 0 }
        }
        stones.values.forEach {
            if (it.isBlack()) {
                libraryBoard[it.position.y][it.position.x] = 1
            } else {
                libraryBoard[it.position.y][it.position.x] = 2
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
