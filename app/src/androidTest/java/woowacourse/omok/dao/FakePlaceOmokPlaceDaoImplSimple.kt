package woowacourse.omok.dao

class FakePlaceOmokPlaceDaoImplSimple(dbHelper: FakeDbHelper) : SimpleOmokPlaceDao(dbHelper) {
    override val boardColumn: String
        get() = "omok_board"
    override val nicknameColumn: String
        get() = "nickname"
    override val tableName: String
        get() = "test"
}
