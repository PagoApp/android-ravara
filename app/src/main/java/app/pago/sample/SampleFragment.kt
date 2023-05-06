package app.pago.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import app.pago.ravara.RavaraRecyclerViewAdapter
import app.pago.ravara.lib.RavaraController
import app.pago.ravara.lib.RavaraControllerBuilder
import app.pago.sample.cells.SimpleBottomSheetCell
import app.pago.sample.cells.SpacerCell
import app.pago.sample.databinding.FragmentSampleBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SampleFragment : Fragment() {

    private lateinit var binding: FragmentSampleBinding;
    private val listAdapter = RavaraRecyclerViewAdapter()
    private lateinit var controller: RavaraController;
    private val viewModel by viewModels<SampleViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun setupUi() {
        controller = RavaraControllerBuilder().useCells(
            arrayOf(
                SpacerCell(),
                SimpleBottomSheetCell()
            )
        ).build()

        listAdapter.bindAdapterToController(controller)
        binding.recyclerView.apply {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    fun setupListeners() {

        binding.addBtn.setOnClickListener {
            viewModel.addItem(binding.editText.text.toString())
        }

        binding.removeBtn.setOnClickListener {
            viewModel.removeItem(binding.editText.text.toString())
        }

        binding.updateBtn.setOnClickListener {
            viewModel.updateItem(binding.editText.text.toString())
        }

        binding.addSpacerBtn.setOnClickListener {
            viewModel.addSpace()
        }

        binding.removeSpacerBtn.setOnClickListener {
            viewModel.removeLastSpace()
        }

    }

    fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.data.collect {
                        controller.addList(it, true)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupListeners()
        setupObservers()
    }
}