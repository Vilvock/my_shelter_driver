package br.com.app5m.appshelterdriver.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.sign

class LinearFixed {

    fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng {
        val lat = (b.latitude - a.latitude) * fraction + a.latitude
        var lngDelta = b.longitude - a.longitude
        // Take the shortest path across the 180th meridian.
        if (abs(lngDelta) > 180) {
            lngDelta -= sign(lngDelta) * 360
        }
        val lng = lngDelta * fraction + a.longitude
        return LatLng(lat, lng)
    }

    fun interpolateAngle(fraction: Float, start: Float, end: Float): Float {
        val shortestAngle = ((((end - start) % 360) + 540) % 360) - 180
        return start + (shortestAngle * fraction) % 360
    }
}