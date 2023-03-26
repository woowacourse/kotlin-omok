package domain.stone

abstract class Stone(val point: Point) {


    override fun equals(other: Any?): Boolean {
        if (other !is Stone) return false
        return (other.point == this.point)
    }

    override fun hashCode(): Int {
        return 1
    }
}
