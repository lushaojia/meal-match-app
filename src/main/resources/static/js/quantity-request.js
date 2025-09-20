const form = document.querySelector('#food-request-form');
const alert = document.querySelector('#alert');
const alertP = document.querySelector('#alert p');

// `latitude` and `longitude` are defined by Thymeleaf.
function goBackToSearch() {
  const url = `/search-results?latitude=${latitude}&longitude=${longitude}`;
  window.location.href = url;
}

async function submitRequest() {
  const formData = new FormData(form);
  const listingId = formData.get('listingId');
  const quantityRequested = formData.get('quantityRequested');

  const url = `/submit-request?listingId=${listingId}&quantityRequested=${quantityRequested}`;
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
  });

  const responseJson = await response.json();
  console.log(responseJson);

  alert.style.visibility = 'visible';
  alert.classList.remove('alert-success', 'alert-danger');

  if (response.ok) {
    alert.classList.add('alert-success');
    alertP.innerText = responseJson.message;
  } else {
    alert.classList.add('alert-danger');
    alertP.innerText = responseJson.error;
  }
}

form.addEventListener('submit', (event) => {
  event.preventDefault();
  submitRequest();
});
