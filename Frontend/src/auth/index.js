export const dologin = (jwttoken, next) => {
  localStorage.setItem("data", JSON.stringify(jwttoken));
  next();
};

// Check login
export const isLoggedIn = () => {
  let data = localStorage.getItem("data");

  if (data != null) {
    return true;
  } else {
    return false;
  }
};

// Logout
export const dologout = (next) => {
  localStorage.removeItem("data");
  next();
};

// Get current user
export const getCurrentUserDetail = () => {
  if (isLoggedIn()) {
    return JSON.parse(localStorage.getItem("data"))?.user;
  } else {
    return undefined;
  }
};

// Get token
export const getToken = () => {
  if (isLoggedIn()) {
    return JSON.parse(localStorage.getItem("data")).token;
  }
  return null;
};