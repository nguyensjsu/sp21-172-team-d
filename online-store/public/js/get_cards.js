import populate_card from './populate_card.js';

/*
iterates through the data returned from /customer/cards endpoint 
from the Starbucks API and creates the HMTL for a card.
<div id="{unique card number goes here}">
  <div class="container flex-start no-padding">
    <span id="balance" class="item" style="font-size: 2em">{card balance}</span>
    <span class="item flex">
      <a
        href="#"
        class="green-text text-darken-3"
        style="font-size: 1.4em"
        >Manage</a
      >
    </span>
  </div>
  <img src="images/card1.jpg" />
</div>
*/
function get_cards() {
  //get customer id
  fetch('http://localhost:8900/customerId')
    .then((res) => res.json())
    .then((uid) => {
      //call Starbucks API
      fetch(`http://localhost:8080/customer/cards/${uid}`)
        .then((res) => res.json())
        .then((data) => {
          let cardList = document.getElementById('card-list');
          console.log(data);
          data.forEach((element) => {
            populate_card(element, cardList, true);
          });
        });
    });
}

//This is JQuery. get_cards() will be called when the HMTL page loads
$(document).ready(get_cards());
