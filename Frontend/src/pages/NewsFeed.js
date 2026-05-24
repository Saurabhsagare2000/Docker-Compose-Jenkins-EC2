import React, { useEffect, useState } from "react";
import { LoadAllPost } from "../services/Post";
import PostData from "../Component/PostData";
import CategorySideMeny from "./CategoriesSideMeny";
import Base from "../Component/Base";

export default function NewFeed() {
  const [postContent, setPostContent] = useState({
    content: [],
    totalElements: 0,
    pageSize: 4,
    totalPages: 0,
    lastPage: false,
    pageNumber: 0,
  });

  // Load posts initially
  useEffect(() => {
    LoadAllPost(0, 4)
      .then((data) => {
        console.log(data);
        setPostContent(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // Change page
  const changePageNumber = (pageNumber, pageSize = 4) => {
    LoadAllPost(pageNumber, pageSize)
      .then((data) => {
        console.log(data);
        setPostContent(data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Base>
      <div className="container">
        <div className="row">
          
          {/* Category Menu */}
          <div
            className="col-md-3"
            style={{ paddingTop: "30px", backgroundColor: "white" }}
          >
            <CategorySideMeny />
          </div>

          {/* Posts */}
          <div className="col-md-9">
            <div className="container-fluid mt-3">
              <div className="row">
                <div className="col-md-12">
                  <h1>Blog Count: {postContent.totalElements}</h1>

                  {postContent.content?.map((post) => (
                    <PostData
                      key={post.postId}
                      title={post.title}
                      content={post.content}
                      postID={post.postId}
                      post={post.user.id}
                    />
                  ))}
                </div>
              </div>

              {/* Pagination */}
              <div className="d-flex justify-content-center mt-4">
                <nav>
                  <ul className="pagination">

                    {/* Previous */}
                    {postContent.pageNumber === 0 ? (
                      <li className="page-item disabled">
                        <button className="page-link">
                          Previous
                        </button>
                      </li>
                    ) : (
                      <li
                        className="page-item"
                        onClick={() =>
                          changePageNumber(postContent.pageNumber - 1)
                        }
                      >
                        <button className="page-link">
                          Previous
                        </button>
                      </li>
                    )}

                    {/* Page Numbers */}
                    {[...Array(postContent.totalPages)].map((_, index) => (
                      <li
                        key={index}
                        className={
                          index === postContent.pageNumber
                            ? "page-item active"
                            : "page-item"
                        }
                        onClick={() => changePageNumber(index)}
                      >
                        <button className="page-link">
                          {index + 1}
                        </button>
                      </li>
                    ))}

                    {/* Next */}
                    {postContent.lastPage ? (
                      <li className="page-item disabled">
                        <button className="page-link">
                          Next
                        </button>
                      </li>
                    ) : (
                      <li
                        className="page-item"
                        onClick={() =>
                          changePageNumber(postContent.pageNumber + 1)
                        }
                      >
                        <button className="page-link">
                          Next
                        </button>
                      </li>
                    )}
                  </ul>
                </nav>
              </div>

            </div>
          </div>
        </div>
      </div>
    </Base>
  );
}