import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { usersPost } from "../services/Post";
import PostData from "../Component/PostData";
import { BASE_URL } from "../services/helper";
import Base from "../Component/Base";
import { getCurrentUserDetail, isLoggedIn } from "../auth";


export default function DeletePost() {
  const { id } = useParams();

  const [posts, setPosts] = useState([]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const loadData = () => {
    usersPost(id)
      .then((data) => {
        console.log("data is ", data);
        setPosts(data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Base>
      <div className="row">

        {/* User Details Section */}
        {isLoggedIn() && (
          <div className="shadow" style={{ width: "100%" }}>
            <div className="card text-center">
              <div className="card-body">

                <div
                  className="image-container mt-3"
                  style={{ maxWidth: "50%" }}
                >
                  <img
                    style={{ width: "200px" }}
                    src={BASE_URL + "/post/image/user.png"}
                    alt="user"
                  />
                </div>

                <div className="card-title">
                  <h1 style={{ fontSize: "30px" }}>User Details</h1>
                </div>

                <div className="card-text">
                  <h4>
                    Username:
                    <span className="text-muted">
                      {" "}
                      {getCurrentUserDetail().name}
                    </span>
                  </h4>

                  <h4>
                    Email:
                    <span className="text-muted">
                      {" "}
                      {getCurrentUserDetail().email}
                    </span>
                  </h4>

                  <h4>
                    About:
                    <span className="text-muted">
                      {" "}
                      {getCurrentUserDetail().about}
                    </span>
                  </h4>

                  <h4>
                    No Of Posts:
                    <span className="text-muted"> {posts.length}</span>
                  </h4>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* User Posts Section */}
        <div className="col-md-6 offset-md-3">
          <div className="container-fluid mt-3">
            <div className="row">
              <div className="col-md-12">
                {posts.map((po) => (
                  <PostData
                    key={po?.postid}
                    title={po?.title}
                    content={po?.content}
                    postID={po?.postid}
                    post={po?.user?.id}
                  />
                ))}
              </div>
            </div>
          </div>
        </div>

      </div>
    </Base>
  );
}