import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import {
  dologout,
  getCurrentUserDetail,
  isLoggedIn
} from "../auth";

export default function CustomNavbar() {
  const [login, setLogin] = useState(false);
  const [user, setUser] = useState(undefined);

  const navigate = useNavigate();

  useEffect(() => {
    setLogin(isLoggedIn());
    setUser(getCurrentUserDetail());
  }, []);

  const logout = () => {
    dologout(() => {
      setLogin(false);
      navigate("/home");
    });
  };

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-dark">
        <div className="container-fluid">

          {/* Brand */}
          <Link className="navbar-brand text-light" to="/home">
            Blog App
          </Link>

          {/* Toggle Button */}
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          {/* Navbar Content */}
          <div
            className="collapse navbar-collapse"
            id="navbarSupportedContent"
          >
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">

              <li className="nav-item">
                <Link
                  className="nav-link active text-light"
                  to="/home"
                >
                  New Feed
                </Link>
              </li>

              <li className="nav-item">
                <Link
                  className="nav-link active text-light"
                  to="/about"
                >
                  About
                </Link>
              </li>

              <li className="nav-item">
                <Link
                  className="nav-link active text-light"
                  to="/services"
                >
                  Services
                </Link>
              </li>

              {/* Dropdown */}
              <li className="nav-item dropdown">
                <Link
                  className="nav-link dropdown-toggle text-light"
                  to="#"
                  id="navbarDropdown"
                  role="button"
                  data-bs-toggle="dropdown"
                >
                  More
                </Link>

                <ul className="dropdown-menu">
                  <li>
                    <Link
                      className="dropdown-item"
                      to="/contact"
                    >
                      Contact Us
                    </Link>
                  </li>

                  <li>
                    <Link
                      className="dropdown-item"
                      to="/facebook"
                    >
                      Facebook
                    </Link>
                  </li>

                  <li>
                    <Link
                      className="dropdown-item"
                      to="/youtube"
                    >
                      Youtube
                    </Link>
                  </li>
                </ul>
              </li>
            </ul>

            {/* Right Side */}
            <div className="d-flex">

              {login && (
                <Link
                  className="nav-link active mx-2 text-light"
                  to={"/user/posts/" + user?.id}
                >
                  Profile
                </Link>
              )}

              {login && (
                <Link
                  className="nav-link active mx-2 text-light"
                  to="/user/dashboard"
                >
                  {user?.email}
                </Link>
              )}

              {login && (
                <button
                  className="btn btn-danger mx-2"
                  onClick={logout}
                >
                  Logout
                </button>
              )}

              {!login && (
                <Link
                  className="nav-link active mx-2 text-light"
                  to="/login"
                >
                  Login
                </Link>
              )}

              {!login && (
                <Link
                  className="nav-link active text-light"
                  to="/signup"
                >
                  Signup
                </Link>
              )}

            </div>
          </div>
        </div>
      </nav>
    </div>
  );
}