package omok.model

data class Vector(val x: Int, val y: Int) {
    operator fun unaryMinus(): Vector = Vector(-x, -y)
}
