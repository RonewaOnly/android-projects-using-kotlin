package com.example.mapbox_ice2

import android.content.ContentValues.TAG
import android.health.connect.datatypes.ExerciseRoute
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Create a map programmatically and set the initial camera
        mapView = MapView(this)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-98.0, 39.5))
                .pitch(0.0)
                .zoom(2.0)
                .bearing(0.0)
                .build()
        )


        // Add the map view to the activity (you can also add it to other views as a child)
        setContentView(mapView)

        if (PermissionsManager.areLocationPermissionsGranted(this.applicationContext)) {
            // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
            with(mapView) {
                location.locationPuck = createDefault2DPuck(withBearing = true)
                location.enabled = true
                location.puckBearing = PuckBearing.COURSE
                viewport.transitionTo(
                    targetState = viewport.makeFollowPuckViewportState(),
                    transition = viewport.makeImmediateViewportTransition()
                )
            }

        } else {
            permissionsManager = PermissionsManager(this.permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }


        //setContentView(R.layout.activity_main)

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {

        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {

                // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
                Toast
                    .makeText(applicationContext,"we good",Toast.LENGTH_SHORT).show()
            } else {

                // User denied the permission
                Toast
                    .makeText(applicationContext,"Nope",Toast.LENGTH_SHORT).show()
            }
        }
    }

}