package woowacourse.omok.dao

class OmokDaoImpl(dbHelper: OmokDbHelper) : OmokDao(dbHelper) {
    override val boardColumn: String
        get() = "board"
    override val nicknameColumn: String
        get() = "nickname"
    override val tableName: String
        get() = "omok_board"
    override val latestStoneColumn: String
        get() = "place"
    override val latestStoneTableName: String
        get() = "latest_place"
}
