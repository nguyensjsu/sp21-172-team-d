console.log('inside signin.js');
require('dotenv').config();
window.addEventListener('DOMContentLoaded', () => {
  var firebaseConfig = {
    apiKey:FIREBASE_apiKey ,
    authDomain:FIREBASE_authDomain ,
    projectId:FIREBASE_projectId ,
    storageBucket:FIREBASE_storageBucket ,
    messagingSenderId:FIREBASE_messagingSenderId ,
    appId:FIREBASE_appId ,
    measurementId:FIREBASE_measurementId,
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.auth().setPersistence(firebase.auth.Auth.Persistence.NONE);

  document.getElementById('login').addEventListener('submit', (event) => {
    event.preventDefault();
    const login = event.target.email.value;
    const password = event.target.password.value;

    firebase
      .auth()
      .signInWithEmailAndPassword(login, password)
      .then((userCredential) => {
        let user = userCredential.user;
        return user.getIdToken().then((idToken) => {
          return fetch('/login', {
            method: 'POST',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ idToken }),
          });
        });
      })
      .then(() => {
        return firebase.auth().signOut();
      })
      .then(() => {
        console.log('redirecting');
        window.location.replace('/home');
      });
  });
});
