add_card = () => {
  fetch('http://localhost:8900/customerId')
    .then((res) => res.json())
    .then((uid) => {
      //call Starbucks API
      fetch(`http://localhost:8080/customer/card/${uid}`, {
        method: 'POST',
      })
        .then((res) => res.json())
        .then((card) => {
          console.log(card);
          window.location.reload();
        });
    });
};
