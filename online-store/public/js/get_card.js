import populate_card from './populate_card.js';

function get_card() {
  let urlString = window.location.href;
  let urlArray = urlString.split('/');
  let cardNum = urlArray[urlArray.length - 1];

  //call Starbucks API
  fetch(`http://localhost:8080/cards/${cardNum}`)
    .then((res) => res.json())
    .then((data) => {
      let card = document.getElementById('card');
      console.log(data);
      populate_card(data, card, false, data.id);
    });
}

$(document).ready(get_card());
