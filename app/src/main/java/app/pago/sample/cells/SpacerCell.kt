package app.pago.sample.cells

import androidx.viewbinding.ViewBinding
import app.pago.recyclerview.decorators.PagoRecyclerViewDecorator
import app.pago.recyclerview.models.PagoRecyclerAdapterBaseItem
import app.pago.recyclerview.models.PagoRecyclerViewCell
import app.pago.sample.databinding.ListItemSpacerBinding
import app.pago.sample.fromDpToPx
import app.pago.sample.cells.data.SpacerCellModel


class SpacerCell : PagoRecyclerViewCell(
    ListItemSpacerBinding::inflate,
    SpacerCellModel::class.java
) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: PagoRecyclerAdapterBaseItem,
        decorators: List<PagoRecyclerViewDecorator>
    ) {
        item as SpacerCellModel
        binding.root.apply {
            setBackgroundResource(item.backgroundResId)
            layoutParams.height = item.height.fromDpToPx
        }
    }
}
