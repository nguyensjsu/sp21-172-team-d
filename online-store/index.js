const express = require('express');
const fetch = require('node-fetch');
var exphbs = require('express-handlebars');

const app = express();

//middleware
app.use(express.json());

//handblebar setup
app.set('view engine', 'hbs');
app.engine(
  'hbs',
  exphbs({
    extname: 'hbs',
    defaultLayout: 'index.hbs',
    layoutsDir: __dirname + '/views/layouts',
    partialsDir: __dirname + '/views/partials',
  })
);

app.get('/', (req, res) => {
  res.render('main');
});

app.get('/profile', (req, res) => {
  res.send('main application will go here');
});

const port = 8900;
app.listen(port);
console.log(`listening on port ${port}`);
