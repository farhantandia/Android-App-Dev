package com.example.bigcityweatherapp

object BigCityData {
        private val CityNames = arrayOf("Tokyo",
            "Delhi",
            "Shanghai",
            "Sao Paulo",
            "Mexico City",
            "Dhaka",
            "Cairo",
            "Beijing",
            "Mumbai",
            "New York City",
            "Karachi",
            "Chongqing",
            "Istanbul",
            "Buenos Aires",
            "Calcutta",
            "Lagos",
            "Moscow",
            "Los Angeles",
            "Paris",
            "Jakarta"
        )

        private val population = arrayOf("37,393,000","30,291,000","27,058,000","22,043,000","21,782,000","21,006,000","20,901,000","20,463,000","20,411,000",
            "18,804,000","16,094,000","15,872,000","15,190,000","15,154,000","14,850,000","14,368,000","12,538,000","12,447,000","11,017,000","10,770,000")
        private val country = arrayOf("Japan","India","China","Brazil","Mexico","Bangladesh","Egypt","China","India",
        "United States","Pakistan","China","Turkey","Argentina","India","Nigeria","Russia","United States","France","Indonesia")

        private val cityImages = intArrayOf(R.drawable.tokyo,
            R.drawable.delhi,
            R.drawable.shanghai,
            R.drawable.saopaulo,
            R.drawable.mexicocity,
            R.drawable.dhaka,
            R.drawable.cairo,
            R.drawable.beijing,
            R.drawable.mumbai,
            R.drawable.newyork,
            R.drawable.karachi,
            R.drawable.congqing,
            R.drawable.istanbul,
            R.drawable.buenos,
            R.drawable.calcutta,
            R.drawable.lagos,
            R.drawable.moscow,
            R.drawable.losangeles,
            R.drawable.paris,
            R.drawable.jakarta)

        val listData: ArrayList<City>
            get() {
                val list = arrayListOf<City>()
                for (position in CityNames.indices) {
                    val city = City()
                    city.name = CityNames[position]
                    city.citypop = population[position]
                    city.country = country[position]
                    city.photo = cityImages[position]
                    list.add(city)
                }
                return list
            }

}