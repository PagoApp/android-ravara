package app.pago.ravara.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class RavaraBaseItem(
    open val id: String,
) : Parcelable