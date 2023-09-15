package com.vhpg.practica2.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vhpg.practica2.R
import com.vhpg.practica2.application.ClothsShopApp
import com.vhpg.practica2.data.ProductRepository
import com.vhpg.practica2.data.remote.ProductDto
import com.vhpg.practica2.databinding.FragmentProductDetailBinding
import com.vhpg.practica2.databinding.FragmentProductsListBinding
import com.vhpg.practica2.ui.adapters.ProductsAdapter
import com.vhpg.practica2.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as ClothsShopApp).repository

        lifecycleScope.launch{
            val call: Call<ProductDto> = repository.getProducts()

            call.enqueue(object: Callback<ProductDto>{
                override fun onResponse(call: Call<ProductDto>, response: Response<ProductDto>) {
                    //binding.pbLoading.visibility = View.GONE
                    var logMessage = getString(R.string.server_response)
                    Log.d(Constants.LOGTAG,"$logMessage ${response.body()}")
                    response.body()?.let{products ->
                        binding.rvGames.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = ProductsAdapter(products){products ->
                                products.id?.let {id ->
                                    //aqui va el codigo para la conexion a los detalles
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, ProductDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDto>, t: Throwable) {
                    var logMessage = getString(R.string.id_Recived)
                    Log.d(Constants.LOGTAG,"$logMessage : ${t.message}")
                    var tMessage = getString(R.string.unavailable_connection)
                    Toast.makeText(requireContext(), "$tMessage", Toast.LENGTH_SHORT).show()
                    //binding.pbLoading.visibility = View.GONE
                }

            })
        }
    }
}