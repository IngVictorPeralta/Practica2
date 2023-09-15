package com.vhpg.practica2.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.vhpg.practica2.R
import com.vhpg.practica2.application.ClothsShopApp
import com.vhpg.practica2.data.ProductRepository
import com.vhpg.practica2.data.remote.ProductDetailDto
import com.vhpg.practica2.databinding.FragmentProductDetailBinding
import com.vhpg.practica2.databinding.FragmentProductsListBinding
import com.vhpg.practica2.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response

private const val PRODUCT_ID = "product_id"

class ProductDetailFragment : Fragment() {

    private var productId: String? = null

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getString(PRODUCT_ID)
            var logMessage = getString(R.string.id_Recived)
            Log.d(Constants.LOGTAG, "$logMessage $productId")


            repository = (requireActivity().application as ClothsShopApp).repository

            lifecycleScope.launch{
                productId?.let{id ->
                    val call: Call<ProductDetailDto> = repository.getProductsDetail(id)
                    call.enqueue(object: Callback<ProductDetailDto>{
                        override fun onResponse(
                            call: Call<ProductDetailDto>,
                            response: Response<ProductDetailDto>
                        ) {
                            val imageResource = when (response.body()?.category) {
                                0-> R.drawable.cat0
                                1 -> R.drawable.cat1
                                2 -> R.drawable.cat2
                                3 -> R.drawable.cat3
                                4 -> R.drawable.cat4
                                5 -> R.drawable.cat5
                                6 -> R.drawable.cat6
                                7 -> R.drawable.cat7
                                else -> R.drawable.cat0
                            }

                            binding.apply {
                                pbLoading.visibility = View.GONE
                                tvTitle.text = response.body()?.name
                                ivIconCat.setImageResource(imageResource)
                                tvLongDesc.text = response.body()?.description
                                var priceResp = response.body()?.price
                                tvPrice.text = "$ ${priceResp.toString()}"
                                var costResp = response.body()?.cost
                                tvCost.text = "$ ${response.body()?.cost.toString()}"
                                var profitResp = priceResp!! - costResp!!
                                tvProfit.text = "$ ${profitResp.toString()}"
                                tvStock.text = response.body()?.stock.toString()
                                tvRestockDate.text = response.body()?.lastRestockDate.toString()
                                Glide.with(requireContext())
                                    .load(response.body()?.url)
                                    .into(ivImage)

                            }
                        }

                        override fun onFailure(call: Call<ProductDetailDto>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE
                            var errorMessage = getString(R.string.Error)
                            Toast.makeText(requireContext(), "$errorMessage : ${t.message}", Toast.LENGTH_SHORT).show()
                        }

                    })

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater,container,false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    companion object {

        @JvmStatic
        fun newInstance(productId: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(PRODUCT_ID, productId)
                }
            }
    }
}