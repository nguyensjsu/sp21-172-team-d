var stripe = Stripe(
  'pk_test_51Iio2XDyQynlG6Qgx4F3sA2Nsba8TfnbNowwvXf5dzlCWiwwoiHoW1CinG8ds7WZTTLMLt899mD3442mjr3Y60Pj001kRSGUZ2'
);
var checkoutButton = document.getElementById('checkout');
var amount = document.getElementById('amount');
var urlString = window.location.href;
var urlArray = urlString.split('/');
var cardNum = urlArray[urlArray.length - 1];

function validateButton() {
  amount = $('select').val();
  if (amount) {
    checkoutButton.disabled = false;
  }
}
checkoutButton.addEventListener('click', function () {
  // Create a new Checkout Session using the server-side endpoint you
  // created in step 3.
  amount = $('select').val();
  var object = { price: amount, card: cardNum };
  console.log(object);
  fetch('/create-checkout-session', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(object),
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (session) {
      return stripe.redirectToCheckout({ sessionId: session.id });
    })
    .then(function (result) {
      // If `redirectToCheckout` fails due to a browser or network
      // error, you should display the localized error message to your
      // customer using `error.message`.
      if (result.error) {
        alert(result.error.message);
      }
      // fetch(`http://localhost:8080/cards/${cardNum}/${amount}`,{
      //   method: "POST"
      // }).then((res) => res.json()).then((card) => console.log(card));
    })
    .catch(function (error) {
      console.error('Error:', error);
    });
});

$(document).ready(function () {
  $('select').formSelect();
});
