package com.arcticfox.cyberpro.ui.expenses.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arcticfox.cyberpro.R
import com.arcticfox.cyberpro.ui.expenses.viewmodels.ExpensesViewModel

class ExpensesFragment : Fragment() {

    companion object {
        fun newInstance() = ExpensesFragment()
    }

    private lateinit var expensesVM: ExpensesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.expenses_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expensesVM = ViewModelProvider(this).get(ExpensesViewModel::class.java)
        //viewModel = ViewModelProviders.of(this).get(ExpensesViewModel::class.java)
    }

}