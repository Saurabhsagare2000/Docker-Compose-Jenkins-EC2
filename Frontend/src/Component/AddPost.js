import React, { useEffect, useState } from "react";
import { getAllCategories } from "../services/category";
import { getCurrentUserDetail } from "../auth";
import { toast } from "react-toastify";
import { uploadImage, DataPost } from "../services/Post";
import Base from "./Base";

export default function AddPost() {
  const [categories, setCategories] = useState([]);
  const [user, setUser] = useState(undefined);

  const [posts, setPosts] = useState({
    title: "",
    content: "",
    categoryId: 0,
    userId: "",
  });

  const [image, setImage] = useState(null);

  useEffect(() => {
    setUser(getCurrentUserDetail());

    getAllCategories()
      .then((data) => {
        setCategories(data);
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // handle input changes
  const fieldChanged = (event) => {
    setPosts({
      ...posts,
      [event.target.name]: event.target.value,
    });
  
  };

  // create post
  const createPost = (event) => {
    event.preventDefault();

    posts.userId = user.id;

    if (posts.title.trim() === "") {
      return alert("Post Title is Necessary");
    }

    if (posts.content.trim() === "") {
      return alert("Post Content is Necessary");
    }

    if (posts.categoryId === 0) {
      return alert("Category is Necessary");
    }

    DataPost(posts)
      .then((data) => {
        

        if (image) {
          uploadImage(image, data.postid)
            .then((res) => {
              toast.success("Image uploaded successfully !!");
              console.log(res);
            })
            .catch((error) => {
              console.log(error);
            });
        }

        toast.success("Post created successfully !!");
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error creating post");
      });
  };

  // add image
  const addImage = (event) => {
    console.log("Image is ", event.target.files[0]);
    setImage(event.target.files[0]);
  };

  return (
    <Base>
      <div className="wrapper d-flex justify-content-center">
        <div className="card" style={{ width: "1000px" }}>
          <div className="card-body">
            <h3 className="text-center">
              What is going in your mind?
            </h3>

            <form onSubmit={createPost}>
              {/* Title */}
              <div className="form-group">
                <label htmlFor="title" className="form-label">
                  Post Title
                </label>

                <input
                  type="text"
                  name="title"
                  className="form-control"
                  id="title"
                  placeholder="Enter here"
                  onChange={fieldChanged}
                />
              </div>

              <br />

              {/* Content */}
              <div className="form-group">
                <label htmlFor="content">Post Content</label>

                <textarea
                  className="form-control"
                  placeholder="Write here..."
                  id="content"
                  name="content"
                  style={{ height: "200px" }}
                  onChange={fieldChanged}
                ></textarea>
              </div>

              <br />

              {/* Category */}
              <div className="form-group">
                <label htmlFor="category">
                  Select Category
                </label>

                <select
                  className="form-select form-select-sm"
                  name="categoryId"
                  onChange={fieldChanged}
                  defaultValue={0}
                >
                  <option disabled value={0}>
                    Select Category
                  </option>

                  {categories.map((category) => (
                    <option
                      key={category.categoryId}
                      value={category.categoryId}
                    >
                      {category?.categoryTitle}
                    </option>
                  ))}
                </select>
              </div>

              <br />

              {/* Image Upload */}
              <div className="mt-3">
                <label htmlFor="image" className="form-label">
                  Upload Image
                </label>

                <input
                  className="form-control form-control-sm"
                  id="image"
                  type="file"
                  onChange={addImage}
                />
              </div>

              <br />

              {/* Buttons */}
              <div className="text-center">
                <button
                  type="submit"
                  className="btn btn-success"
                >
                  Create Post
                </button>

                <button
                  type="reset"
                  className="btn btn-danger ms-2"
                >
                  Reset
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </Base>
  );
}