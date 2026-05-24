import React, { useState } from "react";
import UserContext from "./UserContext";

export default function UserProvider({ children }) {
  const [user, setUser] = useState({
    data: {},
    login: false,
  });

  return (
    <UserContext.Provider value={{ user, setUser }}>
      {children}
    </UserContext.Provider>
  );
}