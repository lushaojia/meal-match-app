# Meal Match App
Meal Match is a platform that connects users with surplus food donated by restaurants, grocery stores, and other establishments. The app provides a user-friendly interface for creating an account, signing in, and browsing nearby food listings. Users can enter their location manually or use the integrated Google Maps interface to discover available listings. Once a listing is selected, they can view details and submit a request to claim the food.

The app’s backend leverages the [Meal-Match API](https://github.com/lushaojia/meal-match-api), which supports geospatial queries to find nearby food listings and updates listing availability as items are requested.

This project was developed collaboratively with three other students for COMS 4156 - Advanced Software Engineering at Columbia University.

## 🔗 Try it out!
[https://meal-match-app-1012636713829.us-east1.run.app](https://meal-match-app-1012636713829.us-east1.run.app)

1. You can register a new account and use these credentials to log in, or use an existing username `test` with password `testtest`.
2. Type in `Lulu Chow Wang Campus Center` and click `Search`.
3. Pick a listing of your choosing and request some items from it.
4. Click on `Back to Search` to see the quantity listed change. You've successfully made a request for a listing!


## ☎️ External API Used for the Backend
- **[Meal-Match API](https://github.com/lushaojia/meal-match-api)** is used to find food listings around a given location and allow the user to request items from listings.
- **Google Firebase Authentication** is used for persistent storage and verification of user credentials. 
- **Google Maps JavaScript API** is used in the frontend to create an interactive map, enable location autocomplete, and handle map interactions
- **Google Maps Places** library provides the autocomplete functionality for location input
- **Google Maps Geocoding API** is used in the backend to reverse geocode coordinates into formatted addresses
