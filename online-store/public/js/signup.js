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
        window.location.replace('/home');
      });
  });
});
