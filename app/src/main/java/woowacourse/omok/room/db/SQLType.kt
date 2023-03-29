package woowacourse.omok.room.db

sealed class SQLType {
    object INTEGER : SQLType() {
        override fun toString(): String = "INTEGER"
    }

    object TEXT : SQLType() {
        override fun toString(): String = "TEXT"
    }
}
