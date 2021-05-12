export default function populate_card(data, card, home) {
  let container = document.createElement('div');
  container.id = data.cardNumber;

  let info = document.createElement('div');
  info.className = 'container flex-start no-padding';

  let balance = document.createElement('span');
  balance.id = 'balance';
  balance.className = 'item card-text';
  balance.textContent = `$${data.balance}`;

  info.appendChild(balance);

  if (home === true) {
    let manage = document.createElement('span');
    manage.className = 'container item';

    let manageLink = document.createElement('a');
    manageLink.className = 'green-text text-darken-3 nav-text';
    manageLink.textContent = 'manage';
    manageLink.setAttribute(
      'href',
      `http://localhost:8900/cards/${data.cardNumber}`
    );
    manage.appendChild(manageLink);
    info.appendChild(manage);
  }

  let img = document.createElement('img');
  img.src = '/images/card1.jpg';

  container.appendChild(info);
  container.appendChild(img);
  card.appendChild(container);
}
