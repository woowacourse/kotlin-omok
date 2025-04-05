package woowacourse.omok.study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class Person1(
    val name: String,
) {
    constructor(name: Int) : this(name.toString())

    companion object {
        operator fun invoke(name: Double): Person1 = Person1(name.toString())
    }
}

class InvokeTest {
    @Test
    fun equals() {
        val primaryPerson = Person1(1)
        val primaryPerson2 = Person1(1)

        assertThat(primaryPerson).isEqualTo(primaryPerson2)
    }

    @Test
    fun equals2() {
        val primaryPerson = Person1(1.0)
        val primaryPerson2 = Person1(1.0)

        assertThat(primaryPerson).isEqualTo(primaryPerson2)
    }
}
