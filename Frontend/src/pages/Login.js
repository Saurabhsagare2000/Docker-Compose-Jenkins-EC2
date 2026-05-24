import React, { useState } from "react";
import { toast } from "react-toastify";
import { login } from "../services/userservice";
import { dologin } from "../auth";
import Base from "../Component/Base";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [loginDetails, setLoginDetails] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();

  // Reset form
  const resetData = () => {
    setLoginDetails({
      username: "",
      password: "",
    });
  };

  // Handle input changes
  const details = (e, property) => {
    setLoginDetails({
      ...loginDetails,
      [property]: e.target.value,
    });
  };

  // Handle login form
  const handleForm = (e) => {
    e.preventDefault();

    console.log(loginDetails);

    if (
      loginDetails.username.trim() === "" ||
      loginDetails.password.trim() === ""
    ) {
      toast.error("Username or Password is blank !!");
      return;
    }

    login(loginDetails)
      .then((jwtToken) => {
        console.log("Token is:", jwtToken);

        dologin(jwtToken, () => {
          console.log("Login details saved in localStorage");
          navigate("/user/dashboard");
        });

        toast.success("Login Successful !!");
      })
      .catch((error) => {
        console.log(error);

        if (error.response && error.response.status === 400) {
          toast.error(error.response.data.message);
        } else {
          toast.error("Enter correct Username and Password !!");
        }
      });
  };

  return (
    <Base>
      <div className="container">
        <div className="col-md-6 offset-md-3 mt-4">
          <div className="card text-bg-dark">
            <div className="card-header">
              <h3>Login</h3>
            </div>

            <div className="card-body">
              <form onSubmit={handleForm}>
                
                {/* Email */}
                <div className="form-group">
                  <label htmlFor="email" className="form-label">
                    Enter Email
                  </label>
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    placeholder="Enter Email"
                    value={loginDetails.username}
                    onChange={(e) => details(e, "username")}
                  />
                </div>

                <br />

                {/* Password */}
                <div className="form-group">
                  <label htmlFor="password" className="form-label">
                    Enter Password
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="password"
                    placeholder="Enter Your Password"
                    value={loginDetails.password}
                    onChange={(e) => details(e, "password")}
                  />
                </div>

                <br />

                {/* Buttons */}
                <div className="text-center">
                  <button
                    type="submit"
                    className="btn btn-light"
                  >
                    Login
                  </button>

                  <button
                    type="button"
                    className="btn btn-light ms-2"
                    onClick={resetData}
                  >
                    Reset
                  </button>
                </div>

              </form>
            </div>
          </div>
        </div>
      </div>
    </Base>
  );
}