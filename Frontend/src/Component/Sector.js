import React from "react";
import { Outlet } from "react-router-dom";
import AddPost from "./AddPost";

export default function Sector() {
  return (
    <div>
     <AddPost />
    </div>
  );
}