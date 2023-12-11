package com.example.newsapp.View

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newswave.NavigationScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class NewsActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }


//        getLocation(this)
        setContent {
            NewsAppTheme {

//                val locationPermissionLauncher =
//                    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//                        if (isGranted) {
//
//                            // Permission granted, you can now access the location
//                            // Place your location-related logic here
//
//                        } else {
//                            // Permission denied, handle accordingly
//                            // You may want to show a message to the user
//                        }
//                    }
//                LocationPermissionScreen(locationPermissionLauncher = locationPermissionLauncher, this)
                val viewModel: NewsViewModel by viewModels()
                NavigationScreen(viewModel = viewModel)
            }
        }
    }

    private fun getLocation(newsActivity: NewsActivity) {

        var fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(newsActivity)

        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(10000) // Set your desired interval in milliseconds
            fastestInterval = TimeUnit.SECONDS.toMillis(500000)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    // Get country code from the location
                    val countryCode = if (getCountryCodeFromLocation(
                            location,
                            newsActivity
                        ) == null
                    ) "uk" else getCountryCodeFromLocation(location, newsActivity)

                    // Use the countryCode as needed
                    Log.d("country code", "Country Code: $countryCode")
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                newsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                newsActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

    private fun getCountryCodeFromLocation(location: Location, context: Context): String? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (addresses != null) {
            return if (addresses.isNotEmpty()) {
                addresses[0].countryCode
            } else {
                null
            }
        }
        return null
    }
}

@Composable
fun LocationPermissionScreen(
    locationPermissionLauncher: ActivityResultLauncher<String>,
    newsActivity: NewsActivity
) {
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                newsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Location Permission Required", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        if (hasLocationPermission) {
            Text("Location permission is granted.")
        } else {
            Text("Location permission is not granted. Grant permission to access location.")

            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Grant Location Permission"),
                onClick = {
                    requestLocationPermission(locationPermissionLauncher)
                }
            )
        }
    }
}

fun requestLocationPermission(locationPermissionLauncher: ActivityResultLauncher<String>) {
    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
}