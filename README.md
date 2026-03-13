# Bike Service App 🏍️

A comprehensive Android application built with Java and Firebase to streamline the process of bike servicing. The app provides separate interfaces for customers and administrators to manage service requests, track deliveries, and maintain service history.

## 🚀 Features

### For Customers (Users)
- **User Authentication**: Secure Login and Registration using Firebase Auth.
- **Profile Management**: Set up and update personal details and bike information (Brand, Model, Number).
- **Service Booking**: Book a service by selecting a date and time using a built-in picker.
- **Track Status**: Monitor the status of your service (Pending, In Progress, Completed).
- **Delivery Management**: View scheduled delivery times for serviced bikes.
- **Feedback**: Submit and view service reviews.
- **Contact Support**: Access contact information for the service center.

### For Administrators (Admin)
- **Admin Dashboard**: Specialized interface for managing the entire service workflow.
- **Manage Requests**: View all new booking requests and update their status.
- **Delivery Scheduling**: Assign delivery dates and times once a service is in progress.
- **Service History**: Maintain a record of all completed services.
- **Review Management**: Monitor customer feedback and ratings.

## 🛠️ Technology Stack
- **Language**: Java
- **UI Framework**: Android Material Design Components
- **Backend/Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Architecture**: Activity-based with RecyclerView for dynamic lists

## 📸 Screen Flow
1. **Welcome Screen**: Initial entry point with login/register options.
2. **Authentication**: Login/Registration screens (Admin login: `baldhapreet@gmail.com`).
3. **User Menu**: Access to Booking, Profile, Delivery status, History, and Reviews.
4. **Admin Dashboard**: Central hub for managing service requests and customer interactions.

## 📋 Prerequisites
- Android Studio Iguana (or newer)
- Firebase Project configured (google-services.json)
- Minimum SDK: API 24 (Android 7.0)

## 🔧 Setup & Installation
1. Clone the repository: `git clone <repository-url>`
2. Open the project in Android Studio.
3. Connect the app to your Firebase project.
4. Sync Gradle and run the app on an emulator or physical device.

---
*Developed for efficient bike service management.*
