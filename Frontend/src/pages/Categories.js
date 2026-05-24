import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import CategorySideMeny from "./CategoriesSideMeny";
import PostData from "../Component/PostData";
import Base from "../Component/Base";

import { LoadPostByCategory } from "../services/Post";

export default function Categories() {
  const [posti, setPostsi] = useState([]);

  const { catgoryId } = useParams();

  useEffect(() => {
    LoadPostByCategory(catgoryId)
      .then((data) => {
        setPostsi([...data]);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [catgoryId]);

  return (
    <Base>
      <div className="container">
        <div className="row">

          {/* Sidebar */}
          <div
            className="col-md-2"
            style={{ paddingTop: "30px", backgroundColor: "white" }}
          >
            <CategorySideMeny />
          </div>

          {/* Posts */}
          <div className="col-md-9">

            {posti.length === 0 ? (
              <h2>No Post In This Category</h2>
            ) : (
              <h1>Blog Count: {posti.length}</h1>
            )}

            <div className="container-fluid mt-3">
              <div className="row">
                <div className="col-md-12">

                  {posti.map((po) => (
                    <PostData
                      key={po.postid}
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
      </div>
    </Base>
  );
}