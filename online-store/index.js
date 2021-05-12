const express = require('express');
const fetch = require('node-fetch');
const path = require('path');
const cookieParser = require('cookie-parser');
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');

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
