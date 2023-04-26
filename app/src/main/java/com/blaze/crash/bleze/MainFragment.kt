package com.blaze.crash.bleze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blaze.crash.bleze.databinding.FragmentMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val decimalFormat = DecimalFormat("###,###")

    private val viewModel by viewModels<MainModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainModel(requireContext().applicationContext) as T
            }
        }
    }
    private var isSpinning = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.balanceLiveData.observe(viewLifecycleOwner) {
            binding.money.text = decimalFormat.format(it)
        }
        binding.spinBtn.setOnClickListener {
            if (isSpinning) return@setOnClickListener
            isSpinning = true

            viewModel.balance -= 50
            val bet1 = listOf(200,100,60,120,20,160,40,80,50)
            val list = listOf(R.anim.rotate_center,R.anim.rotate_center1,R.anim.rotate_center2,R.anim.rotate_center3,R.anim.rotate_center4,R.anim.rotate_center5,R.anim.rotate_center6,R.anim.rotate_center7,R.anim.rotate_center8)
            val rand = java.util.Random()
            val ind = rand.nextInt(list.size)
            val an = AnimationUtils.loadAnimation(requireContext(),list[ind])
            an.fillAfter = true
            binding.imageView.startAnimation(an)
            Completable.create {
                it.onComplete()
            }.delaySubscription(3000,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnComplete {
                viewModel.balance += bet1[ind]
                binding.money2.text = "${bet1[ind]}"
                isSpinning = false
            }.subscribe()

        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()

    }
}