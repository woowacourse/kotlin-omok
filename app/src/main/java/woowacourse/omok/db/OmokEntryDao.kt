package woowacourse.omok.db

import android.content.Context
import androidx.core.content.contentValuesOf

class OmokEntryDao(context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(entry: OmokEntry): OmokEntry {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                OmokContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokContract.STONE_TYPE to entry.stoneType,
                    OmokContract.POSITION to entry.position,
                ),
            )
        return entry.copy(id = id)
    }
}
