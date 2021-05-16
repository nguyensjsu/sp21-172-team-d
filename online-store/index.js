const express = require('express');
const fetch = require('node-fetch');
const path = require('path');
const cookieParser = require('cookie-parser');
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');
const stripe = require("stripe")("sk_test_51Iio2XDyQynlG6QghXMQyA02YlBE3HKUF5gQPHkDYQApUw6QP4lx6pRVCBU6u7CpOQjfQWARw3XxkY13fHtIaEWj00lMadSgqP");
const bodyParser = require('body-parser');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const port = process.env.PORT || 8900;
const app = express();

//middleware
app.use(express.json());
app.use(cookieParser());
app.use(express.static(__dirname + '/public'));

//handblebar setup
//app.set('view engine', 'ejs');


//stripe test
app.use(express.static("."));
app.use(express.json());

app.post('/create-checkout-session', async (req, res) => {
  amount = req.body.price;
  console.log(amount)
  const session = await stripe.checkout.sessions.create({
    payment_method_types: ['card'],
    line_items: [
      {
        price_data: {
          currency: 'usd',
          product_data: {
            name: 'Refill card',
          },
          unit_amount: Number(amount) * 100,
        },
        quantity: 1,
      },
    ],
    mode: 'payment',
    success_url: 'http://localhost:8900/home',
    cancel_url: 'http://localhost:8900/home',
  });

  res.json({ id: session.id });
});


app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname + '/views/signup.html'));
});

app.get('/signin', (req, res) => {
  res.sendFile(path.join(__dirname + '/views/signin.html'));
});

app.get('/signup', (req, res) => {
  res.sendFile(path.join(__dirname + '/views/signup.html'));
});

app.get('/home', (req, res) => {
  const sessionCookie = req.cookies.session || '';

  admin
    .auth()
    .verifySessionCookie(sessionCookie, true)
    .then(() => {
      res.sendFile(path.join(__dirname + '/views/main.html'));
    })
    .catch((error) => res.redirect('./signin'));
});

app.get('/cards/:cardnum', (req, res) => {
  const sessionCookie = req.cookies.session || '';

  admin
    .auth()
    .verifySessionCookie(sessionCookie, true)
    .then(() => {
      res.sendFile(path.join(__dirname + '/views/manage-card.html'));
    })
    .catch((error) => res.redirect('./signin'));
});

app.get('/customerId', (req, res) => {
  const sessionCookie = req.cookies.session || '';
  admin
    .auth()
    .verifySessionCookie(sessionCookie, true)
    .then((data) => {
      res.json(data.uid);
    })
    .catch((error) => res.redirect('./signin'));
});

app.post('/login', (req, res) => {
  console.log('signing up');
  const idToken = req.body.idToken.toString();
  const expiresIn = 60 * 60 * 24 * 3 * 1000;

  admin
    .auth()
    .createSessionCookie(idToken, { expiresIn })
    .then((sessionCookie) => {
      const options = { maxAge: expiresIn, httpOnly: true };
      res.cookie('session', sessionCookie, options);
      res.end(JSON.stringify({ status: 'success' }));
    })
    .catch((error) => res.status(401).send('UNAUTHORIZED REQUEST'));
});

app.get('/logout', (req, res) => {
  res.clearCookie('session');
  res.redirect('/signin');
});

app.post('/webhook', bodyParser.raw({type: 'application/json'}), (request, response) => {
  const event = request.body;
  console.log(event);
  // Handle the event
  switch (event.type) {
    case 'payment_intent.succeeded':
      const paymentIntent = event.data.object;
      console.log(`PaymentIntent for ${paymentIntent.amount} was successful!`);
      // Then define and call a method to handle the successful payment intent.
      // handlePaymentIntentSucceeded(paymentIntent);
      break;
    case 'payment_method.attached':
      const paymentMethod = event.data.object;
      // Then define and call a method to handle the successful attachment of a PaymentMethod.
      // handlePaymentMethodAttached(paymentMethod);
      break;
    default:
      // Unexpected event type
      console.log(`Unhandled event type ${event.type}.`);
  }
  // Return a 200 response to acknowledge receipt of the event
  response.send();
});



app.listen(port);
console.log(`listening on port ${port}`);
