package woowacourse.omok.dao

class FakeDaoImpl(dbHelper: FakeDbHelper) : OmokDao(dbHelper) {
    override val boardColumn: String
        get() = "omok_board"
    override val nicknameColumn: String
        get() = "nickname"
    override val tableName: String
        get() = "test"
}
