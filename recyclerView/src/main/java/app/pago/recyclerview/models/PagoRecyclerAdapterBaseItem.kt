package app.pago.recyclerview.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class PagoRecyclerAdapterBaseItem(
    open val id: String,
) : Parcelable