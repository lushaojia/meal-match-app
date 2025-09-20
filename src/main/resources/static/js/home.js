var map;
var autocomplete;
var marker;

function initMap() {
  // Initialize map
  map = new google.maps.Map(document.getElementById('map'), {
    center: { lat: -34.397, lng: 150.644 },
    zoom: 8,
  });

  const input = document.getElementById('location-input');
  autocomplete = new google.maps.places.Autocomplete(input);
  autocomplete.bindTo('bounds', map);

  // Add a marker for map ping
  marker = new google.maps.Marker({
    map: map,
    draggable: true,
  });

  // Update marker pos when autocomplete suggestion is selected
  autocomplete.addListener('place_changed', () => {
    const place = autocomplete.getPlace();

    if (!place.geometry || !place.geometry.location) {
      alert('No details available for the selected location!');
      return;
    }

    map.setCenter(place.geometry.location);
    map.setZoom(14);

    marker.setPosition(place.geometry.location);
  });

  marker.addListener('dragend', () => {
    const position = marker.getPosition();
    input.value = position.lat() + ', ' + position.lng();
  });

  map.addListener('click', (event) => {
    const position = event.latLng;

    marker.setPosition(position);
    input.value = position.lat() + ', ' + position.lng();
  });
}

function beginSearch() {
  const position = marker.getPosition();
  const latitude = position.lat();
  const longitude = position.lng();

  const url = `/search-results?latitude=${latitude}&longitude=${longitude}`;
  window.location.href = url;
}

// Load the map after the page loads
window.onload = initMap;
