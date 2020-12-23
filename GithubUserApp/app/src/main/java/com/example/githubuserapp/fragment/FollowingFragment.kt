package com.example.githubuserapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.FollowingAdapter
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.main.DetailUserActivity
import com.example.githubuserapp.model.FollowingModel
import com.example.githubuserapp.model.UserModel
import com.example.githubuserapp.parsingAPI.ApiClient
import org.parceler.Parcels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FollowingFragment : Fragment() {
    private lateinit var rvFollowing: RecyclerView
    private lateinit var dataUser: UserModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailUserActivity: DetailUserActivity = activity as DetailUserActivity
        val bundle = detailUserActivity.getIntent().getBundleExtra(UserAdapter.DATA_EXTRA)
        dataUser = Parcels.unwrap<UserModel>(bundle?.getParcelable(UserAdapter.DATA_USER))


        rvFollowing = view.findViewById(R.id.rv_following)
        rvFollowing.layoutManager = LinearLayoutManager(view.context)
        val request: Call<List<FollowingModel>> = ApiClient.apiService.getFollowingUser(dataUser.login)
        request.enqueue(object : Callback<List<FollowingModel>> {
            override fun onResponse(call: Call<List<FollowingModel>>,
                response: Response<List<FollowingModel>>
            ) {
                val listFollowing = ArrayList<FollowingModel>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listFollowing.addAll(response.body()!!)
                        Log.d("TAG RESULT", "onResponse: " + listFollowing.size)
                        rvFollowing.setAdapter(FollowingAdapter(context, listFollowing))
                    }
                } else {
                    Toast.makeText(context, "Request Not Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<FollowingModel>>, t: Throwable) {
                Toast.makeText(context, "Request Failure" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}