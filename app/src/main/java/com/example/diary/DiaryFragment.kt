package com.example.diary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diary.databinding.FragmentSecondBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DiaryFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments?.getBundle("diary")
        binding.tvYdTitle.text = "${bundle?.getString("title")} ${bundle?.getString("emoji")}"
        binding.tvYdDiary.text = bundle?.getString("diary")
        binding.tvYdDiary.movementMethod = ScrollingMovementMethod()
        binding.tvYdTitle.movementMethod = ScrollingMovementMethod()
        binding.clSingleDiary.background = ColorDrawable(Color.parseColor(bundle?.getString("color").toString()))
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(bundle?.getString("color").toString())))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_diary, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                val bundle = arguments?.getBundle("diary")
                Log.d("errorInDelete", bundle?.getString("id").toString())
                Firebase.firestore.collection("diary").document(bundle?.getString("id").toString())
                    .delete()
                    .addOnSuccessListener { findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}