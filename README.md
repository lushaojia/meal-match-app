# Meal Match App
This app provides a platform for users to browse "food listings" posted by restaurants, grocery stores, and other establishments with surplus food. It provides a user-friendly interface that users can navigate to create an account, sign in, and browse nearby food listings. Users can either enter their location manually or navigate via an integrated Google Maps interface to find listings in their area. Once they locate a suitable listing, they can view details about the available food and submit a request to claim it.

This project was developed collaboratively with three other students for COMS 4156 - Advanced Software Engineering at Columbia University.

## 🔗 Live URL
[https://meal-match-app-1012636713829.us-east1.run.app](https://meal-match-app-1012636713829.us-east1.run.app)


## ☎️ External API Used for the Backend
- **Meal Match API** is used to find food listings around a given location and allow the user to request items from listings.
- **Google Firebase Authentication** is used for persistent storage and verification of user credentials. 
- **Google Maps JavaScript API** is used in the frontend to create an interactive map, enable location autocomplete, and handle map interactions
- **Google Maps Places** library provides the autocomplete functionality for location input
- **Google Maps Geocoding API** is used in the backend to reverse geocode coordinates into formatted addresses

## 🧪 Test it out!
1. You can register a new account and use these credentials to log in, or use an existing username `test` with password `testtest`.
2. Type in `Lulu Chow Wang Campus Center` and click `Search`.
3. Pick a listing of your choosing and request some items from it.
4. Click on `Back to Search` to see the quantity listed change. s
