import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  commentOnParticular,
  loadParticularPost,
} from "../services/Post";
import { BASE_URL } from "../services/helper";
import Base from "../Component/Base";
import { isLoggedIn } from "../auth";
import { toast } from "react-toastify";

export default function PostDetail() {
  const [postData, setPostData] = useState({
    user: {},
    category: {},
    content: "",
    comments: [],
    title: "",
    imageName: "",
    addedDate: "",
  });

  const [makeComment, setMakeComment] = useState({
    content: "",
  });

  const { postid } = useParams();

  // Add Comment
  const commentss = () => {
    if (!isLoggedIn()) {
      toast.error("Please login to comment on this post !!");
      return;
    }

    if (makeComment.content.trim() === "") {
      toast.error("Comment cannot be empty !!");
      return;
    }

    commentOnParticular(postid, makeComment)
      .then((data) => {
        setPostData({
          ...postData,
          comments: [...postData.comments, data],
        });

        setMakeComment({
          content: "",
        });

        toast.success("Comment added successfully !!");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // Load Post
  useEffect(() => {
    loadParticularPost(postid)
      .then((data) => {
        setPostData(data);
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [postid]);

  // Date Formatter
  const printDate = (numbers) => {
    return new Date(numbers).toLocaleString();
  };

  return (
    <Base>
      <div className="container">
        <div className="col-md-8 offset-md-2 mt-4">
          <div className="card">
            <div className="card-body text-center mt-3">
              
              <p>
                Posted By <b>{postData.user?.name}</b> on{" "}
                <b>{printDate(postData.addedDate)}</b>
              </p>

              <h3>{postData.title}</h3>

              <div className="card-text">
                <span className="text-muted">
                  {postData.category?.categoryTitle}
                </span>
              </div>

              <hr />

              <div className="image-container mt-3">
                <img
                  src={BASE_URL + "/post/image/" + postData.imageName}
                  alt="Post"
                  style={{ width: "300px" }}
                />
              </div>

              <div className="card-text mt-3">
                {postData.content}
              </div>
            </div>
          </div>

          {/* Comments Section */}
          <div className="row mt-3">
            <div className="col-md-12">
              <h3>
                Comments ({postData?.comments?.length})
              </h3>

              <div>
                {postData.comments &&
                  postData.comments.map((c, index) => (
                    <div key={index} className="card mt-2 p-2">
                      <p className="text-muted">{c.content}</p>
                    </div>
                  ))}
              </div>
            </div>
          </div>

          {/* Add Comment */}
          <div className="row mt-3 mb-3">
            <div className="col-md-12">
              <textarea
                className="form-control"
                placeholder="Enter Comment here"
                value={makeComment.content}
                onChange={(event) =>
                  setMakeComment({
                    content: event.target.value,
                  })
                }
              ></textarea>

              <button
                type="button"
                className="btn btn-primary mt-2"
                onClick={commentss}
              >
                Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    </Base>
  );
}