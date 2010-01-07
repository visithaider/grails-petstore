package gps

class GeoPoint {

    double latitude = 0.0, longitude = 0.0
    String address, city, state, zip, country

    @Override
    String toString() {
        "$address $city, $state, $zip, $latitude, $longitude"
    }

}