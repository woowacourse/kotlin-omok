package model.domain.tools

data class Location(val coordinationX: Coordination, val coordinationY: Coordination) {
    companion object {
        operator fun invoke(x: Int, y: Int) = Location(Coordination.from(x), Coordination.from(y))
    }
}
