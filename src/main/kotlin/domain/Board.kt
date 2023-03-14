package domain

import domain.Color.BLACK
import domain.Color.WHITE

class Board {
    private val _blackStones: MutableList<BlackStone> = mutableListOf()
    val blackStones: List<BlackStone> get() = _blackStones.toList()

    private val _whiteStones: MutableList<WhiteStone> = mutableListOf()
    val whiteStones: List<WhiteStone> get() = _whiteStones.toList()

    val turn: Color
        get() {
            if (blackStones.size == whiteStones.size) return BLACK
            return WHITE
        }
}
