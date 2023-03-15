package view

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

class BoardView(blackStones: Set<Stone>, whiteStones: Set<Stone>) {

    private val list2d = mutableListOf<MutableList<String>>()

    init {
        for (y in YCoordinate.Y_MAX_RANGE downTo YCoordinate.Y_MIN_RANGE) {
            val ma = mutableListOf<String>()
            ma.add("%3s ".format(y))
            for (x in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE) {
                when {
                    blackStones.contains(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> ma.add(BLACK_STONE)
                    whiteStones.contains(Stone(XCoordinate.of(x), YCoordinate.of(y))) -> ma.add(WHITE_STONE)
                    x == XCoordinate.X_MIN_RANGE && y == YCoordinate.Y_MAX_RANGE -> ma.add(LEFT_TOP)
                    x == XCoordinate.X_MAX_RANGE && y == YCoordinate.Y_MAX_RANGE -> ma.add(RIGHT_TOP)
                    x == XCoordinate.X_MIN_RANGE && y == YCoordinate.Y_MIN_RANGE -> ma.add(LEFT_BOTTOM)
                    x == XCoordinate.X_MAX_RANGE && y == YCoordinate.Y_MIN_RANGE -> ma.add(RIGHT_BOTTOM)
                    x == XCoordinate.X_MIN_RANGE -> ma.add(LEFT)
                    x == XCoordinate.X_MAX_RANGE -> ma.add(RIGHT)
                    y == YCoordinate.Y_MAX_RANGE -> ma.add(TOP)
                    y == YCoordinate.Y_MIN_RANGE -> ma.add(BOTTOM)
                    else -> ma.add(MIDDLE)
                }
                if (x != XCoordinate.X_MAX_RANGE) ma.add("──")
            }
            list2d.add(ma)
        }
        list2d.add(mutableListOf("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"))
    }

    override fun toString(): String {
        val sb = StringBuilder()
        with(sb) {
            list2d.forEach {
                sb.append(it.joinToString(""))
                sb.append("\n")
            }
        }
        return sb.toString()
    }

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"

        private const val LEFT_TOP = "┌"
        private const val TOP = "┬"
        private const val RIGHT_TOP = "┐"
        private const val LEFT = "├"
        private const val MIDDLE = "┼"
        private const val RIGHT = "┤"
        private const val LEFT_BOTTOM = "└"
        private const val BOTTOM = "┴"
        private const val RIGHT_BOTTOM = "┘"
    }
}