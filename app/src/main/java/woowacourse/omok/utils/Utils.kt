package woowacourse.omok.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun createVectorDrawable(
    context: Context,
    vectorResId: Int,
): Drawable? {
    return ContextCompat.getDrawable(context, vectorResId)
}
