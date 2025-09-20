const loginFormContainer = document.getElementById('loginFormContainer');
const registerFormContainer = document.getElementById('registerFormContainer');
const pageTitle = document.getElementById('page-title');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const loginUsernameInput = document.getElementById('login-username');
const loginPasswordInput = document.getElementById('login-password');
const registerUsernameInput = document.getElementById('register-username');
const registerPasswordInput = document.getElementById('register-password');
const alert = document.querySelector('#alert');
const alertP = document.querySelector('#alert p');

function hideAlert() {
    alert.classList.add('not-visible');
    alertP.textContent = '';
}


// Show register form
function showRegister() {
  if (!alert.classList.contains('not-visible')) {
      hideAlert()
  }
  loginFormContainer.classList.remove('active');
  registerFormContainer.classList.add('active');
  pageTitle.innerText = 'Register';
  loginForm.reset(); // Clear fields in form.
}

// Show log-in form
function showLogin() {
  if (!alert.classList.contains('not-visible')) {
        hideAlert()
  }
  registerFormContainer.classList.remove('active');
  loginFormContainer.classList.add('active');
  pageTitle.innerText = 'Login';
  registerForm.reset(); // Clear fields in form.
}

async function loginCallback() {
  const username = loginUsernameInput.value;
  const password = loginPasswordInput.value;

  // Call backend API to authenticate user
  const response = await fetch('/login/authenticate', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
  });

  const result = await response.json();

  alert.style.display = 'block';
  alert.classList.remove('alert-success', 'alert-danger', 'not-visible');
  if (response.ok) {
    console.log('response ok')
    alert.classList.add('alert-success');
    alertP.innerText = result.message;

    setTimeout(() => {
      window.location.href = '/home';
    }, 1000);
  } else {
    console.log('response not ok')
    alert.classList.add('alert-danger');
    alertP.innerText = result.message || 'Login failed. Please try again.';

    loginForm.reset();
  }
}

async function registerCallback() {
  const username = registerUsernameInput.value;
  const password = registerPasswordInput.value;

  // Call backend API to register user
  const response = await fetch('/login/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
  });

  const result = await response.json();

  alert.style.display = 'block';
  alert.classList.remove('alert-success', 'alert-danger', 'not-visible');

  if (response.ok) {
    console.log('response ok')
    alert.classList.add('alert-success');
    alertP.innerText = result.message;

    setTimeout(() => {
          showLogin();
        }, 1500);
  } else {
    console.log('response not ok')
    alert.classList.add('alert-danger');
    alertP.innerText = result.message || 'Registration failed. Please try again.';

    registerForm.reset();
  }
}

loginForm.addEventListener('submit', (event) => {
  event.preventDefault();
  loginCallback();
});
registerForm.addEventListener('submit', (event) => {
  event.preventDefault();
  registerCallback();
});
