package com.example.diary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.databinding.FragmentFirstBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("diary" to bundle))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}