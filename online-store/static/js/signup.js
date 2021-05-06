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
  firebase.initializeApp(firebaseConfig);
  firebase.auth().setPersistence(firebase.auth.Auth.Persistence.NONE);

  document.getElementById('signin').addEventListener('submit', (event) => {
    event.preventDefault();
    const email = event.target.email.value;
    const password = event.target.password.value;

    firebase
      .auth()
      .createUserWithEmailAndPassword(email, password)
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
        let user = firebase.auth().currentUser;
        let customer = {
          customerId: user.uid,
        };

        /*creating new customer object through Starbucks-API*/
        fetch('http://localhost:8080/customer', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },
          body: JSON.stringify(customer),
        })
          .then((res) => res.json())
          .then((customer) => console.log(customer))
          .catch((err) => console.log(err));
      })
      .then(() => {
        return firebase.auth().signOut();
      })
      .then(() => {
        console.log('redirecting');
        window.location.replace('/profile');
      });
  });
});
