# COMP30022 IT-Project: Olditivity

## Overview 

Olditivity is an android application whose primary users are the elderly. The application is designed as a social media platform for users, so they can plan and meet together for a range of different activities. In this app, users can create & join events, call event hosts, navigate to event locations and message members of the event group.

## List of features 
* User accounts
* Create events
* Explore events
* Join or leave events
* Send messages & images with event group members
* Navigate to events with GPS
* Call event host 

## Download & Usage 
### Device Requirements 
* Android Device or emulator running SDK 26 or above 
* Android Version 8.0 or above 

* Some features require internet connection & GPS tracking to work correctly, application is best used on an Android device 

### Installation
1. Clone/download repo
2. Open project folder in Android Studio
3. Build project
4. (Optional) Connect Android device to machine (computer/laptop)
5. Run on emulator or connected Android device

### Server Setup 

Install by first installing [node.js](https://nodejs.org/en/). After sever can be installed using the command npm install.

We used [Heroku](https://heroku.com/) to host the server.


## Testing 
Tests can be found in the [Oldivity/app/src/test/java/com/example/oldivity](https://github.com/COMP30022-18/Oldtivity_server/tree/UI/Oldivity/app/src/test/java/com/example/oldivity) folder.

Sever tests were made using chai and mocha and can be found int the [Sever/test](https://github.com/COMP30022-18/Oldtivity_server/tree/UI/Server/test) folder. Sever tests can be run with the command npm test.

## Built With 
[Firebase](https://firebase.google.com/) - Webhost for database

[Google Maps](https://developers.google.com/maps/documentation/javascript/directions) - API for map

[Sinch](https://www.sinch.com/) - Call function

[Okhttp3](https://github.com/square/okhttp) - Build and send HTTP request

[Mockito](https://site.mockito.org/) - App testing

[Espresso](https://developer.android.com/training/testing/espresso/) - App tsting

[Node.js](https://nodejs.org/en/) - Sever environment

[Node-geocoder](https://www.npmjs.com/package/node-geocoder) - API for geocodes

[Express](https://expressjs.com/) - Node.js framework

[Heroku](https://www.heroku.com/) - Sever web host

[Chai](https://www.chaijs.com/) - Server testing

[Mocha](https://mochajs.org/) - Server testing




## Authors

  Teresa Lieu
  
  Anita Naseri
  
  Doan Minh Trung Nguyen
  
  Nicholas Sujecki
  
  Xinbo Sun

## Acknowledgements


