import { myAxios } from "./helper";

export const getAllCategories = () => {
  return myAxios
    .get("/categories/")
    .then((response) => {
      return response.data;
    });
};