add_card = () => {
  fetch('http://localhost:8900/customerId')
    .then((res) => res.json())
    .then((uid) => {
      //call Starbucks API
      fetch(`http://starbucks.pedrosatech.com/api/customer/card/${uid}`, {
        method: 'POST',
      })
        .then((res) => res.json())
        .then((card) => {
          console.log(card);
          let cardList = document.getElementById('card-list');
          let container = document.createElement('div');
          container.id = card.cardNumber;

          let info = document.createElement('div');
          info.className = 'container flex-start no-padding';

          let balance = document.createElement('span');
          balance.id = 'balance';
          balance.className = 'item card-text';
          balance.textContent = `$${card.balance}`;

          info.appendChild(balance);

          let manage = document.createElement('span');
          manage.className = 'container item';

          let manageLink = document.createElement('a');
          manageLink.className = 'green-text text-darken-3 nav-text';
          manageLink.textContent = 'manage';
          manageLink.setAttribute(
            'href',
            `http://localhost:8900/cards/${card.cardNumber}`
          );
          manage.appendChild(manageLink);
          info.appendChild(manage);

          let img = document.createElement('img');
          img.src = `/images/card1.png`;

          container.appendChild(info);
          container.appendChild(img);
          cardList.appendChild(container);
          //window.location.reload();
        });
    });
};
