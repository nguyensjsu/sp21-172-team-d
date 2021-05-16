const express = require('express');
const fetch = require('node-fetch');
const path = require('path');
const cookieParser = require('cookie-parser');
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');
const stripe = require('stripe')(
  'sk_test_51IredNELdiv23YD49fQbtIjQn756WgbaAQ29nO5YjL3EsnaVQNUhKsdCMcXgvI7IKrIiEjRZGV3dOz4cXoa6qLUm00DNVYfppE'
);

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const port = process.env.PORT || 8900;
const app = express();

//middleware
app.use(express.json());
app.use(cookieParser());
app.use(express.static(__dirname + '/public'));

//stripe test
app.use(express.static('.'));

app.post('/create-checkout-session', async (req, res) => {
  let amount = req.body.price;
  let cardNum = req.body.card;
  console.log(req.body);
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
    success_url: `http://localhost:8900/cards/${cardNum}`,
    cancel_url: `http://localhost:8900/cards/${cardNum}`,
    client_reference_id: cardNum, //this will pass the cardnum to the data object that stripe creates
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

app.listen(port);
console.log(`listening on port ${port}`);
