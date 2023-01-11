package app.pago.sample.cells.data

import android.os.Parcelable
import androidx.annotation.ColorRes
import app.pago.ravara.models.RavaraBaseItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleBottomSheetModel(
    override val id: String,
    var text: String?,
    var iconResId: Int,
    var iconResIdRight: Int? = null,
    @ColorRes val iconTint: Int? = null,
    var backgroundResId: Int? = null,
    val isDisabled: Boolean = false,
    val listener: () -> Unit,
    val hasBottomDivider: Boolean = true
) : RavaraBaseItem(id), Parcelable
