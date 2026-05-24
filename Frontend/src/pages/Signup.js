import React, { useState } from "react";
import { userservice } from "../services/userservice";
import { toast } from "react-toastify";
import Base from "../Component/Base";

export default function Signup() {
  const [data, setData] = useState({
    name: "",
    email: "",
    password: "",
    about: "",
  });

  const [error, setError] = useState({
    errors: {},
    isError: false,
  });

  // Handle input changes
  const handleChange = (event, property) => {
    setData({
      ...data,
      [property]: event.target.value,
    });
  };

  // Reset form
  const resetData = () => {
    setData({
      name: "",
      email: "",
      password: "",
      about: "",
    });

    setError({
      errors: {},
      isError: false,
    });
  };

  // Submit form
  const submitForm = (event) => {
    event.preventDefault();

    userservice(data)
      .then((resp) => {
        console.log(resp);
        toast.success("User Registered Successfully !!");
        resetData();
      })
      .catch((error) => {
        console.log(error);

        setError({
          errors: error,
          isError: true,
        });

        toast.error("Form data is invalid !!");
      });
  };

  return (
    <Base>
      <div className="container">
        <div className="col-md-6 offset-md-3 mt-4">
          <div className="card text-bg-dark">
            <div className="card-header">
              <h3>Fill Information to Register</h3>
            </div>

            <div className="card-body">
              <form onSubmit={submitForm}>
                
                {/* Name */}
                <div className="form-group">
                  <label htmlFor="name" className="form-label">
                    Enter Name
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="name"
                    placeholder="Enter Name"
                    onChange={(e) => handleChange(e, "name")}
                    value={data.name}
                  />
                  {error.isError && (
                    <div className="text-danger">
                      {error.errors?.response?.data?.name}
                    </div>
                  )}
                </div>

                <br />

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
                    onChange={(e) => handleChange(e, "email")}
                    value={data.email}
                  />
                  {error.isError && (
                    <div className="text-danger">
                      {error.errors?.response?.data?.email}
                    </div>
                  )}
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
                    placeholder="Enter Password"
                    onChange={(e) => handleChange(e, "password")}
                    value={data.password}
                  />
                  {error.isError && (
                    <div className="text-danger">
                      {error.errors?.response?.data?.password}
                    </div>
                  )}
                </div>

                <br />

                {/* About */}
                <div className="mb-3">
                  <label htmlFor="about" className="form-label">
                    About
                  </label>
                  <textarea
                    className="form-control"
                    id="about"
                    rows="3"
                    onChange={(e) => handleChange(e, "about")}
                    value={data.about}
                  ></textarea>

                  {error.isError && (
                    <div className="text-danger">
                      {error.errors?.response?.data?.about}
                    </div>
                  )}
                </div>

                {/* Buttons */}
                <div className="text-center">
                  <button type="submit" className="btn btn-light">
                    Register
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