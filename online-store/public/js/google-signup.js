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

  var provider = new firebase.auth.GoogleAuthProvider();

  document
    .getElementById('google-signin')
    .addEventListener('click', (event) => {
      firebase
        .auth()
        .signInWithPopup(provider)
        .then((result) => {
          /** @type {firebase.auth.OAuthCredential} */
          var credential = result.credential;

          // This gives you a Google Access Token. You can use it to access the Google API.
          var token = credential.accessToken;
          // The signed-in user info.
          var user = result.user;
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
          fetch('http://localhost:8080/api/customer', {
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
        })
        .catch((error) => {
          // Handle Errors here.
          var errorCode = error.code;
          var errorMessage = error.message;
          // The email of the user's account used.
          var email = error.email;
          // The firebase.auth.AuthCredential type that was used.
          var credential = error.credential;

          if (error.code === 'auth/account-exists-with-different-credential') {
            // Step 2.
            // User's email already exists.
            // The pending Google credential.
            var pendingCred = error.credential;
            // The provider account's email address.
            var email = error.email;
            // Get sign-in methods for this email.
            auth.fetchSignInMethodsForEmail(email).then(function (methods) {
              // Step 3.
              // If the user has several sign-in methods,
              // the first method in the list will be the "recommended" method to use.
              if (methods[0] === 'password') {
                // Asks the user their password.
                // In real scenario, you should handle this asynchronously.
                var password = promptUserForPassword(); // TODO: implement promptUserForPassword.
                auth
                  .signInWithEmailAndPassword(email, password)
                  .then(function (result) {
                    // Step 4a.
                    return result.user.linkWithCredential(pendingCred);
                  })
                  .then(function () {
                    // Google account successfully linked to the existing Firebase user.
                    goToApp();
                  });
                return;
              }
              // All the other cases are external providers.
              // Construct provider object for that provider.
              // TODO: implement getProviderForProviderId.
              var provider = getProviderForProviderId(methods[0]);
              // At this point, you should let the user know that they already has an account
              // but with a different provider, and let them validate the fact they want to
              // sign in with this provider.
              // Sign in to provider. Note: browsers usually block popup triggered asynchronously,
              // so in real scenario you should ask the user to click on a "continue" button
              // that will trigger the signInWithPopup.
              auth.signInWithPopup(provider).then(function (result) {
                // Remember that the user may have signed in with an account that has a different email
                // address than the first one. This can happen as Firebase doesn't control the provider's
                // sign in flow and the user is free to login using whichever account they own.
                // Step 4b.
                // Link to Google credential.
                // As we have access to the pending credential, we can directly call the link method.
                result.user
                  .linkAndRetrieveDataWithCredential(pendingCred)
                  .then(function (usercred) {
                    // Google account successfully linked to the existing Firebase user.
                    goToApp();
                  });
              });
            });
          }
        });
    });
});
