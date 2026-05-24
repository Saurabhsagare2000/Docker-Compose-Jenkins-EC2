import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { isLoggedIn, getCurrentUserDetail } from "../auth";
import { deletePost } from "../services/Post";

export default function PostData(props) {
  const [user, setUser] = useState(null);
  const [login, setLogin] = useState(false);

  useEffect(() => {
    setUser(getCurrentUserDetail());
    setLogin(isLoggedIn());
  }, []);

  const handleDeletePost = () => {
    deletePost(props.postID)
      .then((data) => {
        console.log("Deleted:", data);
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="card mt-3">
      <div className="card-body">

        <h1>{props.title}</h1>

        <div className="card-text">
          <p>{props.content}</p>
        </div>

        <Link
          to={"/postdetail/" + props.postID}
          className="btn btn-secondary"
        >
          Read More
        </Link>

        {login && user && user.id === props.postUserId && (
          <button
            type="button"
            className="btn btn-danger mx-2"
            onClick={handleDeletePost}
          >
            Delete
          </button>
        )}

        {login && user && user.id === props.postUserId && (
          <Link
            to={"/user/update/" + props.postID}
            className="btn btn-warning mx-2"
          >
            Update
          </Link>
        )}

      </div>
    </div>
  );
}