package com.example.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.databinding.FragmentFirstBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), SeeDiary {

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
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("diary" to bundle))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = Firebase.auth.currentUser
        auth?.let {
            id = it.uid
        }

        Firebase.firestore
            .collection("diary")
            .whereEqualTo("user_id", id)
            .get().addOnSuccessListener {
                val diaryList = mutableListOf<DiaryModel>()

                for (document in it) {

                    var data = document.toObject(DiaryModel::class.java)
                    data.id = document.id
                    diaryList.add(data)
                }
                Log.d("document", diaryList.toString())
                val rvDiaryList = binding.rvDiaryList

                rvDiaryList.adapter = DiaryListAdapter(this, diaryList)
                rvDiaryList.layoutManager = LinearLayoutManager(this.context)
            }
        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_FirstFragment_to_addDiaryFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}