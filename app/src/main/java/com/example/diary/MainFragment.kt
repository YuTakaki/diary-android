package com.example.diary

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.databinding.FragmentFirstBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainFragment : Fragment(), SeeDiary {

    private var _binding: FragmentFirstBinding? = null
    lateinit var id : String;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun seeDiary(diary: DiaryModel) {
        Log.d("diaryList", diary.toString())
        val list = listOf(diary.date, diary.diary, diary.title)
        val bundle = Bundle()
        bundle.putString("diary", diary.diary)
        bundle.putString("date", diary.date.toString())
        bundle.putString("title", diary.title )
        bundle.putString("id", diary.id )
        bundle.putString("color", diary.color )
        bundle.putString("emoji", diary.emoji )
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("diary" to bundle))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_diarylist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#21262D")))
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = Firebase.auth.currentUser
        auth?.let {
            id = it.uid
        }
//        Firebase.firestore
//            .collection("diary")
//            .whereEqualTo("user_id", id)
//            .orderBy("date", Query.Direction.DESCENDING)
//            .get()
//            .addOnCompleteListener {
//                val x = mutableListOf<DiaryModel>()
//                for (d in it){
//                    d.da
//                    x.add(d.data)
//                }
//
//            }
        val query = Firebase.firestore
            .collection("diary")
            .whereEqualTo("user_id", id)
            .orderBy("date", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<DiaryModel>().setQuery(query, DiaryModel::class.java)
            .setLifecycleOwner(this).build()

        val rvDiaryList = binding.rvDiaryList
        rvDiaryList.adapter = DiaryAdapter(this, options)
        rvDiaryList.layoutManager = LinearLayoutManager(this.context)

        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_FirstFragment_to_addDiaryFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.calendar -> {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val datePickerDialog = DatePickerDialog(activity as MainActivity, DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    Log.d("asdads", Date(year, month, day).toString())
                    val query = Firebase.firestore.collection("diary")
                        .whereEqualTo("user_id", id)
                        .whereGreaterThan("date", Timestamp(Date(year, month, day)))
                    val options = FirestoreRecyclerOptions.Builder<DiaryModel>().setQuery(query, DiaryModel::class.java)
                        .setLifecycleOwner(this).build()
                    val rvDiaryList = binding.rvDiaryList
                    rvDiaryList.adapter = DiaryAdapter(this, options)
                    rvDiaryList.layoutManager = LinearLayoutManager(this.context)

                }, year,month,day)
                datePickerDialog.show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}