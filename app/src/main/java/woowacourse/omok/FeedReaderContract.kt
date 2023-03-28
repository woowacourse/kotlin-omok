package woowacourse.omok

import android.provider.BaseColumns

/**
 * 체계적인 자체 문서화 방식으로 스키마의 레이아웃을 명시적으로 지정하는
 * 계약 클래스라고 하는 컴패니언 클래스를 생성하면 도움이 될 수 있습니다.
 *
 * 계약 클래스는 URI, 테이블 및 열의 이름을 정의하는 상수를 유지하는 컨테이너입니다.
 * 계약 클래스를 통해 동일한 패키지의 다른 모든 클래스에 동일한 상수를 사용할 수 있습니다.
 * 이렇게 하면 어느 한 곳에서 열 이름을 변경하고 이 변경사항을 코드 전체에 전파할 수 있습니다.
 */
object FeedReaderContract {
    val SQL_CREATE_ENTRIES: String = """
        |CREATE TABLE ${FeedEntry.TABLE_NAME} (
        |${BaseColumns._ID} INTEGER PRIMARY KEY,
        |${FeedEntry.COLUMN_NAME_TITLE} TEXT,
        |${FeedEntry.COLUMN_NAME_SUBTITLE} TEXT
        |)
    """.trimMargin()

    const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"

    object FeedEntry : BaseColumns {
        const val TABLE_NAME: String = "entry"
        const val COLUMN_NAME_TITLE: String = "title"
        const val COLUMN_NAME_SUBTITLE: String = "subtitle"
    }
}
