package app.pago.sample.cells

import androidx.viewbinding.ViewBinding
import app.pago.ravara.decorators.RavaraDecorator
import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.models.RavaraCell
import app.pago.sample.databinding.ListItemSpacerBinding
import app.pago.sample.fromDpToPx
import app.pago.sample.cells.data.SpacerCellModel


class SpacerCell : RavaraCell(
    ListItemSpacerBinding::inflate,
    SpacerCellModel::class.java
) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: RavaraBaseItem,
        decorators: List<RavaraDecorator>
    ) {
        item as SpacerCellModel
        binding.root.apply {
            setBackgroundResource(item.backgroundResId)
            layoutParams.height = item.height.fromDpToPx
        }
    }
}
