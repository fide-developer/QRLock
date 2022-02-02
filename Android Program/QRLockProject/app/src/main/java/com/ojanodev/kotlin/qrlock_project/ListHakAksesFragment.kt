package com.ojanodev.kotlin.qrlock_project


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojanodev.kotlin.qrlock_project.ViewModel.ListHakAksesViewModel
import com.ojanodev.kotlin.qrlock_project.adapter.HakAksesAdapter
import com.ojanodev.kotlin.qrlock_project.adapter.ListHakAksesAdapter
import com.ojanodev.kotlin.qrlock_project.model.Users
import kotlinx.android.synthetic.main.fragment_list_hak_akses.*

class ListHakAksesFragment : Fragment() {
    private var listHakAkseses : ArrayList<Users> = ArrayList()
    private lateinit var adapter : ListHakAksesAdapter
    private lateinit var layoutManagers: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_hak_akses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManagers = LinearLayoutManager(activity)

        adapter = activity?.let { ListHakAksesAdapter(it,listHakAkseses) }!!

        rv_listHakAkses.layoutManager = layoutManagers

        val vm = ViewModelProviders.of(this)[ListHakAksesViewModel::class.java]
        vm.getData().observe(this, Observer<ArrayList<Users>>{
            adapter = activity?.let { it1 -> ListHakAksesAdapter(it1,it) }!!

            rv_listHakAkses.adapter = adapter
        })
    }
}
