# AndroidApp5
My superpodcast
AndroidApp5 – SuperPodcast App

Assignment 8 – Networking & Repository (Android)

📌 Overview

SuperPodcast is an Android application created for Assignment 8 to explore networking, repositories, mock APIs, and audio playback in Android development.
The app is inspired by the iTunes Podcast example but uses custom mock data to safely demonstrate functionality without requiring API keys.

This assignment focuses on core functionality, not final UI polish.

🎯 Assignment Objectives

This project demonstrates the following Android concepts:

Networking using Retrofit-style APIs

Repository pattern for data handling

Mock API implementation (no HTTP 401 / API key issues)

Searching podcasts with advanced criteria

Viewing podcast details and episodes

Playing podcast audio

Git version control and GitHub integration

🛠️ Features Implemented
🔍 Podcast Search

Search podcasts using a query

Uses mock networking data

Returns multiple podcast results

⭐ Advanced Criteria (Repository Layer)

Filtering by:

Word count in podcast title

Regex matching

Listen score

Global rank

Sorting options:

Relevance

Lowest / Highest score

Lowest / Highest rank

📄 Podcast Details

Displays:

Podcast title

Publisher

Description

Episode list

▶️ Audio Playback

Plays podcast episode audio

Uses streaming audio URLs

Implemented using an audio player bar

🧪 Mock Networking

No real API calls required

Mock data provided via:

MockLnApi

MockLnData

Ensures stable development and testing

🗂️ Project Structure
com.example.superpodcast
│
├── data
│   ├── db
│   │   ├── AppDatabase
│   │   ├── DbProvider
│   │   ├── SubscriptionDao
│   │   └── SubscriptionEntity
│   │
│   ├── ln
│   │   ├── LnApi
│   │   ├── LnModels
│   │   ├── LnNetwork
│   │   ├── MockLnApi
│   │   └── MockLnData
│
├── repo
│   └── PodcastRepository
│
├── ui
│   ├── player
│   │   └── AudioPlayerBar
│   │
│   ├── screens
│   │   ├── SearchScreen
│   │   ├── DetailsScreen
│   │   └── SubscriptionsScreen
│
├── AppRoot
└── MainActivity

🔌 Networking Approach

Uses mock implementation instead of real API calls

Prevents authentication errors (HTTP 401)

Allows safe testing and learning

Real API integration can be added later

🧠 Learning Outcomes

Through this assignment, I learned:

How networking layers work in Android

How to design a repository-based architecture

How to mock APIs for development

How to stream and play audio

How to manage commits and push changes to GitHub properly

📦 GitHub Repository

🔗 Repository Link:
https://github.com/samreenbaig-code/AndroidApp5

📅 Assignment Status

✅ Core functionality implemented
✅ Code committed and pushed to GitHub
✅ Ready for review

✍️ Author

Samreen Baig
Mobile Application Development
Assignment 8 – Android

If you want, I can also:

✨ Shorten it (if instructor prefers minimal README)

🧑‍🏫 Rewrite it in very academic tone

📸 Add a “Screenshots” section

✅ Check it against grading rubric

Just tell me 👍
