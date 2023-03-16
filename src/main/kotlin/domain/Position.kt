package domain

data class Position(val coordinateY: Coordinate, val coordinateX: Coordinate) {

    fun getX() = coordinateX.value
    fun getY() = coordinateY.value
}
