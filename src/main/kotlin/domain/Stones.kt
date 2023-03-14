package domain

open class Stones private constructor(val values: List<Stone>) {

    constructor(vararg stones: Stone) : this(stones.toList())
}
