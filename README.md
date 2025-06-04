Assignment 2: Location based login
Objective: Develop a simple native mobile application that periodically fetches the device location (both in foreground as well as in background) to login into the office dashboard.

GeoTagged Login App (LocationLoginApp)
This Android app restricts user login access based on their geographical location. The app allows a user to log in only when they are within a pre-defined latitude and longitude range (geofencing). It uses real-time location tracking and logs out the user automatically if they move outside the permitted area.

Features

- Login access restricted to specific latitude and longitude.
- Real-time geolocation tracking using Android's `LocationManager`.
- Auto logout when user leaves the defined zone.
- Session state management using `StateFlow`.
- MVVM Architecture for separation of concerns.
- Custom splash screen and clean UI design.
- Android runtime permissions handling (foreground & background location access).

Tech Stack

- Kotlin
- MVVM Architecture
- StateFlow / Coroutine Flow
- Android Location APIs
- Runtime Permissions (Android 10+ compatible)

Project Structure

![image](https://github.com/user-attachments/assets/975ad518-f15b-4917-8b58-35d49049cddb)



How It Works

1. User opens the app → Splash screen checks and requests permissions.
2. If permissions are granted → Redirect to Login screen.
3. After successful login, the app starts `LocationService`.
4. The service checks real-time GPS coordinates every 10 sec:
    - If inside defined area → Session remains active.
    - If outside the area → Session is marked false, user is redirected to login screen with a toast message.

Steps Followed During Development

1. Created a new Android project using Kotlin with MVVM architecture.
2. Designed splash, login, and dashboard UI using XML.
3. Implemented a location-tracking service using `LocationManager`.
4. Defined login zone via fixed latitude and longitude and distance calculation.
5. Created `SessionRepository` with `MutableStateFlow` to track login state across activities.
6. Used coroutine collectors (`collectLatest`) to observe session state and act on changes.
7. Implemented runtime permission request using `ActivityCompat`:
    - Asked for foreground location permission first.
    - Requested background permission only after foreground was granted.
8. Used `BaseActivity` to handle common permission logic.
9. Tested app behavior by simulating location changes.


Challenges Faced & Solutions

| Challenge | Solution |
|----------|----------|
| App not prompting for permissions on install | Used step-by-step permission request logic in `SplashActivity` |
| Background location permission not working on Android 11+ | Ensured foreground permissions were granted before requesting background |
| Wrong coordinates not preventing login | Verified correct format (decimal degrees) and fixed coordinate check logic |
| Session state not reflected across activities | Switched from simple boolean to `MutableStateFlow` for reactive updates |
| Toast and redirection not happening | Observed flow in `DashboardActivity` and redirected on session logout |


Output Behavior

- ✅ App asks for all permissions on launch (Android 10+ friendly).
- ✅ Only logs in user when in correct GPS range.
- ✅ Auto logs out user and redirects when they exit the area.
- ✅ Displays toast message: “You are not in the correct location.”


Improvements for Future

Store and display login history.
