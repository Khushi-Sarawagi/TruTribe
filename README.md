
# Technical Requirements Document (TRD)

## 1. Overview of the Idea

### Purpose:
TruTribe is a social media application designed for Android that enables users to join interest-based communities such as Book Lovers, Gym Freaks, and Coders etc. To ensure authenticity, users must pass a quiz before joining a community. The app facilitates local and global connections, meetups, chat functionalities, and interactive challenges to enhance engagement.

### Objectives:
- Provide a platform for users to connect with like-minded individuals.
- Ensure community authenticity through a mandatory quiz.
- Offer a seamless experience with features such as meetups, chat, events, and challenges.
- Use an engaging leaderboard system to motivate user participation.
- Deliver an intuitive UI with a robust backend to handle community interactions efficiently.

## 2. Problem Statement

### Background:
Existing social media platforms lack mechanisms to ensure the authenticity of interest-based communities. Many groups become inactive or diluted with unrelated content, reducing user engagement.

### Issues Identified:
- Lack of verification before joining a community.
- Limited features for structured community interactions.
- Absence of gamification elements like challenges and leaderboards.
- Unorganized event management within interest-based communities.

### Impact:
Without a structured approach, users may lose interest due to a lack of meaningful interactions. TruTribe aims to address this gap by offering a community-driven platform with engagement-enhancing features.

## 3. Proposed Solution

### Solution Overview:
TruTribe provides a structured and engaging platform where users join verified communities through quizzes, participate in events, and engage in community-based discussions and challenges.

### Key Components:
- User Authentication (Login/Signup/Forgot Password)
- Interest-Based Quiz with a Timer
- Home Page with Community Overview
- Community-Specific Pages (Events, Posts, Chats, Challenges, Leaderboards)
- Real-time Chat System using WebSockets
- Gamification with Challenges and Leaderboards
- Event and Meetup Management
- Notification System
- Profile and Settings Pages
- Custom REST APIs for seamless data flow between frontend and backend

### Benefits:
- Ensures community authenticity through quiz-based verification.
- Encourages engagement through events, challenges, and leaderboards.
- Provides a structured and visually appealing interface.
- Supports real-time chat for seamless communication.
- Uses custom APIs to enhance performance and scalability.

## 4. Required or Desired Tech Stack

### Frontend Technologies:
- **Language:** Kotlin
- **UI Development:** XML for designing layouts
- **Frameworks & Libraries:** Material Design
- **Networking:** Retrofit for API calls
- **Authentication:** Firebase Authentication

### Backend Technologies:
- **Language:** Python
- **Framework:** Django REST Framework (DRF) or Flask
- **Database:** PostgreSQL
- **Real-time Communication:** WebSockets using Django Channels
- **API Development:** Custom REST APIs built using Django/Flask

### Infrastructure:
- **Cloud Services:** Firebase (Authentication, Push Notifications)
- **Hosting:** AWS EC2, Firebase Hosting

### Integration Tools:
- **Messaging:** Firebase Cloud Messaging (FCM)
- **API Management:** Postman for testing APIs
- **CI/CD:** GitHub Actions for automated testing and deployment

### Additional Tools:
- **Development:** Android Studio, Postman, VS Code
- **Collaboration:** GitHub, Notion
- **Testing:** PyTest, Selenium

## 5. Functional Requirements / Feature List

### High-Level Features:
- **Authentication:** Login, Signup, Forgot Password
- **Quiz System:** Timer-based quiz for community access
- **Home Page:** Displays user-joined communities, upcoming challenges, and events
- **Community Page:** Displays events, posts, challenges, leaderboard, and chats
- **Real-Time Chat:** Community-wide chat feature using WebSockets
- **Leaderboard:** Tracks and ranks users based on points
- **Events & Meetups:** Allows users to plan and join meetups
- **Challenges:** Users can participate in community-specific challenges
- **Notifications:** Alerts for upcoming events, challenge updates, and messages
- **Profile & Settings:** Allows users to manage their personal data and preferences

### User Roles and Permissions:
- **Standard User:** Can join communities, participate in events, chat, and complete challenges.

## 6. Breakdown of Features

### Custom API Development
- **Feature:** Django/Flask-based REST APIs for seamless frontend-backend communication.
- **Sub-Features:**
  - Secure authentication API using JWT.
  - CRUD operations for managing users, communities, events, and challenges.
  - Real-time WebSocket-based API for chat.
- **Acceptance Criteria:** APIs should function seamlessly with the frontend.

## 7. Detailed Implementation Strategy

### Phase 1 – Planning and Design (Week 1-2)
- **Requirement Analysis:**
  - Define project scope and key features.
  - Identify user personas and workflows.
- **UI/UX Design:**
  - Design wireframes and mockups using Figma.
  - Finalize UI components using XML layouts and Material Design principles.
- **Backend Setup:**
  - Set up Django/Flask framework.
  - Define database schema in PostgreSQL.
  - Plan REST API endpoints and WebSocket communication protocols.

### Phase 2 – Backend Development (Week 3-6)
- **User Authentication Module:**
  - Implement Firebase Authentication.
  - Create JWT-based authentication for API security.
- **Quiz System:**
  - Develop quiz logic with a timer.
  - Restrict backward navigation in questions.
- **Community API Development:**
  - Build CRUD APIs for communities.
  - Implement user verification for joining communities.
- **Real-Time Chat System:**
  - Implement WebSockets using Django Channels.
  - Enable message storage and retrieval.

### Phase 3 – Frontend Development (Week 5-8)
- **Develop Authentication Screens:**
  - Create Login, Signup, and Forgot Password screens.
- **Implement Quiz UI:**
  - Build quiz screen with timer and question types.
- **Home Page UI:**
  - Display joined communities, challenges, and events.
- **Community Page:**
  - Implement chat, leaderboard, and event display.
- **Integrate APIs with Retrofit for data fetching.**

### Phase 4 – Testing and Quality Assurance (Week 9-10)
- **Unit Testing:**
  - Test individual components using JUnit and PyTest.
- **Integration Testing:**
  - Validate API functionality using Postman.
- **User Acceptance Testing (UAT):**
  - Conduct live user testing to gather feedback.

### Phase 5 – Deployment and Rollout (Week 11)
- **Backend Deployment:**
  - Deploy Django/Flask API on AWS EC2.
  - Configure database and WebSocket services.
- **Frontend Deployment:**
  - Package and upload APK to Google Play Store.

### Phase 6 – Post-Launch and Maintenance (Week 12+)
- **Monitor Performance:**
  - Track system usage and response times.
- **Bug Fixes & Enhancements:**
  - Release patches and feature updates.
- **Scale System:**
  - Optimize API response and enhance scalability.

