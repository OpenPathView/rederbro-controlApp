map = new L.Map('map');

// create the tile layer with correct attribution
var osmUrl='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib='Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors';
var osm = new L.TileLayer(osmUrl, {minZoom: 1, maxZoom: 12, attribution: osmAttrib});

// start the map in South-East England
map.setView(new L.LatLng(51.3, 0.7),12);
map.addLayer(osm);

var markers = [];

var addMarker = function(cord){
    var marker = L.marker(cord);
    marker.addTo(map);
    map.setView(marker.getLatLng(), 12);
    markers.push(marker);
}

var clearMarker = function(){
    for (marker in markers) {
        map.removeLayer(marker);
    }
}