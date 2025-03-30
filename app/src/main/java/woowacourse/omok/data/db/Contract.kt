package woowacourse.omok.data.db

interface Contract {
    val createQuery: String
    val deleteQuery: String
}
