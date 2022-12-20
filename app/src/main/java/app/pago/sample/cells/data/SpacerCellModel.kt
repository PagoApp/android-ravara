package app.pago.sample.cells.data

import app.pago.recyclerview.models.PagoRecyclerAdapterBaseItem
import kotlinx.parcelize.Parcelize

/**
 * @param backgroundResId The background of the spacer
 * @param height The height of the spacer in dp
 */
@Parcelize
data class SpacerCellModel(val backgroundResId: Int, val height: Int, override val id: String) :
    PagoRecyclerAdapterBaseItem(id)