package model.domain.tools

data class Dot(val row: Int, val col: Int) {
    companion object {
        fun from(index: Int, size: Int) = Dot(row = index / size, col = index % size)
    }
}
