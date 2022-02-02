package com.ojanodev.kotlin.qrlock_project


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
import com.ojanodev.kotlin.qrlock_project.ViewModel.RequestHakAksesViewModel
import com.ojanodev.kotlin.qrlock_project.adapter.ListHakAksesAdapter
import com.ojanodev.kotlin.qrlock_project.adapter.RequestHakAksesAdapter
import com.ojanodev.kotlin.qrlock_project.model.Users
import kotlinx.android.synthetic.main.fragment_list_hak_akses.*
import kotlinx.android.synthetic.main.fragment_request_hak_akses.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RequestHakAksesFragment : Fragment() {
    private var listRequest : ArrayList<Users> = ArrayList()
    private lateinit var adapter : RequestHakAksesAdapter
    private lateinit var layoutManagers: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_hak_akses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManagers = LinearLayoutManager(activity)

        adapter = activity?.let { RequestHakAksesAdapter(it,listRequest) }!!

        rv_reqHakAkses.layoutManager = layoutManagers

        val vm = ViewModelProviders.of(this)[RequestHakAksesViewModel::class.java]
        vm.getData().observe(this, Observer<ArrayList<Users>>{
            adapter = activity?.let { it1 -> RequestHakAksesAdapter(it1,it) }!!

            rv_reqHakAkses.adapter = adapter
        })
    }
}
