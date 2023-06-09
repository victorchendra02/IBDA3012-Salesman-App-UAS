package id.ac.ibda.pads.proyekuas.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ibda.pads.proyekuas.Adapter.RecyclerAdapter
import id.ac.ibda.pads.proyekuas.Model.CartItem
import id.ac.ibda.pads.proyekuas.Model.ProductModel
import id.ac.ibda.pads.proyekuas.Utils.AccessTokenManager
import id.ac.ibda.pads.proyekuas.Utils.RetrofitObject
import id.ac.ibda.pads.proyekuas.Utils.ShoppingCart
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class HomepageVM: ViewModel() {
    private val apiService = RetrofitObject.apiService
    val itemList = MutableLiveData<MutableList<ProductModel>>()

    fun getProducts() {
        AccessTokenManager.getAccessToken { token ->
            if (token != null) {
                apiService.getProducts("Bearer ${token}").enqueue(object: Callback<List<ProductModel>> {
                    override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                        Log.e("GET_PRODUCTS_FAILED", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<List<ProductModel>>,
                        response: Response<List<ProductModel>>
                    ) {
                        val listOfProducts = response.body()!!.toMutableList()
                        itemList.value = listOfProducts
                        Log.d("GET_PROODUCTS", itemList.value.toString())
                    }
                })
            }
        }
    }

    fun addToCart(position: Int, qty: Int, product: ProductModel) {
        val cartItem = CartItem(product.id, product.name, product.price, qty)
        Log.d("ADD_TO_CART_HOMEPAGE_VM", "Item Position: $position")
        Log.d("ADD_TO_CART_HOMEPAGE_VM", "CartItem: $cartItem")
        ShoppingCart.addItem(cartItem)
    }

    fun observeItemList(adapter: RecyclerAdapter) {
        itemList.observeForever { newItemList ->
            adapter.updateDataList(newItemList)
        }
    }
}