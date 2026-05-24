    import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
  loadParticularPost,
  updatePost
} from "../services/Post";

import { getCurrentUserDetail } from "../auth";
import { toast } from "react-toastify";
import { getAllCategories } from "../services/category";

import Base from "../Component/Base";

export default function Update() {
  const { postID } = useParams();

  const [categories, setCategories] = useState([]);
  const [post, setPost] = useState(null);

  const navigate = useNavigate();

  // Update post
  const updateForm = (e) => {
    e.preventDefault();

    updatePost(
      {
        ...post,
        category: {
          categoryId: post.categoryId
        }
      },
      postID
    )
      .then((data) => {
        console.log(data);
        toast.success("Post updated successfully!");
        navigate("/home");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // Handle form fields
  const fieldChanged = (e) => {
    setPost({
      ...post,
      [e.target.name]: e.target.value
    });
  };

  // Load data
  useEffect(() => {
    getAllCategories()
      .then((data) => {
        setCategories(data);
      })
      .catch((error) => {
        console.log(error);
      });

    loadParticularPost(postID)
      .then((data) => {
        console.log(data);

        setPost({
          ...data,
          categoryId: data.category.categoryId
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }, [postID]);

  // Check ownership
  useEffect(() => {
    if (post != null) {
      if (post.user.id !== getCurrentUserDetail().id) {
        toast.error("This is not your post");
        navigate("/home");
      }
    }
  }, [post, navigate]);

  const updateHtml = () => {
    return (
      <div className="wrapper d-flex justify-content-center">
        <div
          className="card"
          style={{ width: "1000px" }}
        >
          <div className="card-body">
            <h3 className="text-center">
              Update Your Post
            </h3>

            <form onSubmit={updateForm}>

              {/* Title */}
              <div className="form-group">
                <label
                  htmlFor="title"
                  className="form-label"
                >
                  Post Title
                </label>

                <input
                  type="text"
                  name="title"
                  className="form-control"
                  id="title"
                  placeholder="Enter here"
                  onChange={fieldChanged}
                  value={post.title}
                />
              </div>

              <br />

              {/* Content */}
              <div className="form-group">
                <label htmlFor="content">
                  Post Content
                </label>

                <textarea
                  className="form-control"
                  name="content"
                  style={{ height: "200px" }}
                  onChange={fieldChanged}
                  value={post.content}
                ></textarea>
              </div>

              <br />

              {/* Category */}
              <div className="form-group">
                <label htmlFor="category">
                  Select Category
                </label>

                <select
                  className="form-select"
                  name="categoryId"
                  onChange={fieldChanged}
                  value={post.categoryId}
                >
                  <option disabled value={0}>
                    Select category
                  </option>

                  {categories.map((category) => (
                    <option
                      key={category.categoryId}
                      value={category.categoryId}
                    >
                      {category.categoryTitle}
                    </option>
                  ))}
                </select>
              </div>

              <br />

              {/* Buttons */}
              <div className="text-center">
                <button
                  type="submit"
                  className="btn btn-success"
                >
                  Update Post
                </button>

                <button
                  type="button"
                  className="btn btn-danger ms-2"
                  onClick={() => navigate("/home")}
                >
                  Cancel
                </button>
              </div>

            </form>
          </div>
        </div>
      </div>
    );
  };

  return (
    <Base>
      <div>
        {post && updateHtml()}
      </div>
    </Base>
  );
}