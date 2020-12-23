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
import com.example.githubuserapp.adapter.FollowerAdapter
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.main.DetailUserActivity
import com.example.githubuserapp.model.FollowerModel
import com.example.githubuserapp.model.UserModel
import com.example.githubuserapp.parsingAPI.ApiClient
import org.parceler.Parcels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FollowerFragment : Fragment() {

    lateinit var rvFollower :RecyclerView
    lateinit var dataUser :UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        //Mengambil data dari search user

        val detailUserActivity: DetailUserActivity = activity as DetailUserActivity
        val bundle = detailUserActivity.getIntent().getBundleExtra(UserAdapter.DATA_EXTRA)
        dataUser = Parcels.unwrap<UserModel>(bundle?.getParcelable(UserAdapter.DATA_USER))

        rvFollower = view.findViewById(R.id.rv_follower)
        rvFollower.layoutManager = LinearLayoutManager(view.context)

        val request: Call<List<FollowerModel>> =ApiClient.apiService.getFollowerUser(dataUser.login)
        request.enqueue(object : Callback<List<FollowerModel>> {
            override fun onResponse(call: Call<List<FollowerModel>>,
                                    response: Response<List<FollowerModel>>    ) {
                val listFollower =   ArrayList<FollowerModel>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listFollower.addAll(response.body()!!)
                        Log.d("TAG RESULT", "onResponse: " + listFollower.size)
                        rvFollower.setAdapter(FollowerAdapter(context, listFollower))
                    }
                } else {
                    Toast.makeText(context, "Request Not Success", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<List<FollowerModel>>, t: Throwable) {
                Toast.makeText(context, "Request Failure" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
