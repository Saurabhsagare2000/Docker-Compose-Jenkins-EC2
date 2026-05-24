import React from "react";
import CustomNavbar from "./CustomNavbar";

export default function Base({
  title = "Welcome to our website",
  children
}) {
  return (
    <div>
      <div className="container-fluid p-0 m-0">
        <CustomNavbar />
        {children}
      </div>
    </div>
  );
}