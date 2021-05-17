function get_rewards() {
  //get customer id
  fetch('http://localhost:8900/customerId')
    .then((res) => res.json())
    .then((uid) => {
      //call Starbucks API
      fetch(`http://localhost:8080/customer/${uid}`)
        .then((res) => res.json())
        .then((data) => {
          let rewards = document.getElementById('rewards');
          let icon = document.createElement('i');
          let amount = document.createElement('span');
          let title = document.getElementById('rewards_category');
          let description = document.getElementById('rewards_description');
          console.log(data);
          icon.className = 'medium material-icons yellow-text text-darken-1';
          icon.textContent = 'star';
          amount.textContent = data.rewardsPoints;

          rewards.appendChild(amount);
          rewards.appendChild(icon);

          if (data.rewardsPoints >= 400) {
            title.textContent = 'Select merchandise or at-home coffee';
            description.textContent =
              'Take home a signature cup, a bag of coffee or your choice of select coffee accessories.';
          } else if (data.rewardsPoints >= 200) {
            title.textContent = 'Salad, sandwich or protein box';
            description.textContent =
              'Nourish your day with a hearty Chipotle Chicken Wrap or Eggs & Cheese Protein Box.';
          } else if (data.rewardsPoints >= 150) {
            title.textContent = 'Handcrafted drink, hot breakfast or parfait';
            description.textContent =
              'Have a really good morning with a breakfast sandwich, oatmeal or your favorite drink.';
          } else if (data.rewardsPoints >= 50) {
            title.textContent = 'Brewed hot coffee, bakery item or hot tea';
            description.textContent =
              'Pair coffee cake or an almond croissant with your fresh cup of hot brew.';
          } else if (data.rewardsPoints >= 25) {
            title.textContent = 'Customize your drink';
            description.textContent =
              'Make your drink just right with an extra espresso shot, dairy substitute or a dash of your favorite syrup.';
          }
        });
    });
}

$(document).ready(get_rewards());
