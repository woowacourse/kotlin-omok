package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SetStudy {
    @Test
    fun set() {
        val set: MutableSet<Person> = mutableSetOf(Person("뭉치", 20), Person("지오", 20))

        val find = set.find { it.name == "뭉치" }!!
        set -= find
        set += find.copy(age = 21)
        assertThat(set.find { it.name == "뭉치" }!!.age).isEqualTo(21)
    }
}

data class Person(
    val name: String,
    val age: Int,
)
