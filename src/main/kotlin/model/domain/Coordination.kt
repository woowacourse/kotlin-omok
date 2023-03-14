package model.domain

@JvmInline
value class Coordination private constructor(val value: Int) {
    companion object {
        fun from(value: Int): Coordination? {
            if (value !in 1..15) return null
            return Coordination(value)
        }
    }
}
