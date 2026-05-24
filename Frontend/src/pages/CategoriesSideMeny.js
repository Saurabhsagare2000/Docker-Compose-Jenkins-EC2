import React, { useEffect, useState } from "react";
import { getAllCategories } from "../services/category";
import { Link } from "react-router-dom";

export default function CategorySideMeny() {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getAllCategories()
      .then((data) => {
        setCategories(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <ul className="list-group">
      <Link className="list-group-item" to="/home">
        All Blogs
      </Link>

      {categories &&
        categories.map((cat, index) => (
          <Link
            className="list-group-item shadow-sm"
            to={"/category/" + cat.categoryId}
            key={index}
          >
            {cat.categoryTitle}
          </Link>
        ))}
    </ul>
  );
}