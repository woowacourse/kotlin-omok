package model.domain

@JvmInline
value class Coordination private constructor(val value: Int) {
    companion object {
        private const val MINIMUM_VALUE = 1
        private const val MAXIMUM_VALUE = 15
        private val range = MINIMUM_VALUE..MAXIMUM_VALUE

        fun from(value: Int): Coordination? {
            if (value !in range) return null
            return Coordination(value)
        }
    }
}
