package domain.stone

class Stone(
    val position: StonePosition,
    val type: StoneType,
) {
    companion object {
        fun from(position: StonePosition, type: StoneType): Stone =
            Stone(position, type)
    }
}
