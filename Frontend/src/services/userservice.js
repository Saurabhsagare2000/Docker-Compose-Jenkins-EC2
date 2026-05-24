import { myAxios } from "./helper";

// Register user
export const userservice = (user) => {
  return myAxios
    .post("/auth/register", user)
    .then((response) => response.data);
};

// Login user
export const login = (loginDetails) => {
  return myAxios
    .post("/auth/login", loginDetails)
    .then((response) => response.data);
};