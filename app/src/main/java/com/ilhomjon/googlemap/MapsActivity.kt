package com.ilhomjon.googlemap

import com.google.android.gms.maps.model.*
import com.ilhomjon.googlemap.databinding.ActivityMapsBinding

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    var locationPermissionGranted = false
    var lastKnownLocation: Location? = null
    private val TAG = "MapsActivity"


    var INTERVAL = 1000 * 10
    var FASTEST_INTERVAL = 1000 * 5
    var mLocationRequest : LocationRequest? = null
    var mGoogleApiClient:GoogleApiClient? = null
    var mCurrentLocation:Location? = null
    var mLastUptadeTime:String = ""


    private fun createLocationRequest(){
        mLocationRequest = LocationRequest()
        mLocationRequest?.interval = INTERVAL.toLong()
        mLocationRequest?.fastestInterval = FASTEST_INTERVAL.toLong()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        if(!isGooglePlayServicesAvilable()){
            finish()
        }

        createLocationRequest()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()


        //joylashuvni aniqlash
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    fun startLocationUpdates(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient, mLocationRequest, this
        )
        Log.d(TAG, "Location update started ..............: ")
    }

    private fun isGooglePlayServicesAvilable(): Boolean {
        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        return if (ConnectionResult.SUCCESS == status){
            true
        }else{
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show()
            false
        }

    }

//    private fun getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            locationPermissionGranted = true
//        } else {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        locationPermissionGranted = false
//        when (requestCode) {
//            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    locationPermissionGranted = true
//                }
//            }
//        }
//        updateLocationUI()
//    }

//    private fun updateLocationUI() {
//        if (map == null) {
//            return
//        }
//        try {
//            if (locationPermissionGranted) {
//                map?.isMyLocationEnabled = true
//                map?.uiSettings?.isMyLocationButtonEnabled = true
//            } else {
//                map?.isMyLocationEnabled = false
//                map?.uiSettings?.isMyLocationButtonEnabled = false
//                lastKnownLocation = null
//                getLocationPermission()
//            }
//        } catch (e: SecurityException) {
//            Log.e("Exception: %s", e.message, e)
//        }
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {


//        map = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(40.383204, 71.782721)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Codial"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))


//        // Add polylines to the map.
//        // Polylines are useful to show a route or some other connection between points.
//        val polyline1 = googleMap.addPolyline(
//            PolylineOptions()
//            .clickable(true)
//            .add(
//                LatLng(-35.016, 143.321),
//                LatLng(-34.747, 145.592),
//                LatLng(-34.364, 147.891),
//                LatLng(-33.501, 150.217),
//                LatLng(-32.306, 149.248),
//                LatLng(-32.491, 147.309)))
//
//        // Position the map's camera near Alice Springs in the center of Australia,
//        // and set the zoom factor so most of Australia shows on the screen.
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-34.364, 147.891), 10f))
//
//        // Set listeners for click events.
//        googleMap.setOnPolylineClickListener(this)


//        //polygon
//        val polygon1 = googleMap.addPolygon(PolygonOptions()
//            .clickable(true)
//            .add(
//                LatLng(-27.457, 153.040),
//                LatLng(-33.852, 151.211),
//                LatLng(-37.813, 144.962),
//                LatLng(-34.928, 138.599)))
//// Store a data object with the polygon, used here to indicate an arbitrary type.
//        polygon1.tag = "alpha"
//// Style the polygon.
//        googleMap.setOnPolygonClickListener(this)


//        //joylashuvni aniqlash
//        this.map = googleMap
//        updateLocationUI()
//        getDeviceLocation()

        map = googleMap

        if (mCurrentLocation!=null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mCurrentLocation?.latitude ?: 0.0, mCurrentLocation?.longitude ?: 0.0),
            10.0f)
            )
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                    )
                                )
                            )
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), 17.0f
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(LatLng(40.383204, 71.782721), 4.0f)
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onPolylineClick(p0: Polyline) {
        Toast.makeText(
            this, "${p0.points[0].latitude}" +
                    " ${p0.points[0].longitude}", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPolygonClick(p0: Polygon) {
        Toast.makeText(
            this,
            "${p0.points[0].latitude} ${p0.points[0].longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient?.isConnected() == true){
            startLocationUpdates()
            Log.d(TAG, "Location update request..................")
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
            mGoogleApiClient, this
        )
        Log.d(TAG, "Location update stopped......................")
    }

    override fun onResume() {
        super.onResume()
        if (mGoogleApiClient?.isConnected() == true){
            startLocationUpdates()
            Log.d(TAG, "Location resumed.........................")
        }
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient?.disconnect()
    }

    override fun onLocationChanged(p0: Location) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        mCurrentLocation = p0
        Toast.makeText(this, "${p0.latitude}   ${p0.longitude}", Toast.LENGTH_SHORT).show()


    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}