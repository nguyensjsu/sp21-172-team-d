console.log('inside signin.js');

window.addEventListener('DOMContentLoaded', () => {
  var firebaseConfig = {
    apiKey: 'AIzaSyA9TbKO5ICQ9Axzj4hlycGtVwTAZTJNz6E',
    authDomain: 'starbucks-online-store.firebaseapp.com',
    projectId: 'starbucks-online-store',
    storageBucket: 'starbucks-online-store.appspot.com',
    messagingSenderId: '192573308957',
    appId: '1:192573308957:web:6f01ac7a0ea1a920c0c6f4',
    measurementId: 'G-0J40M88H1G',
  };
  // Initialize Firebase
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  } else {
    firebase.app(); // if already initialized, use that one
  }
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
