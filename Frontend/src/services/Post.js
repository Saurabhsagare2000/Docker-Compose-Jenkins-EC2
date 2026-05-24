import { myAxios, privateAxios } from "./helper";

// Create Post
export const DataPost = (PostData) => {
  console .log("Post data is at", PostData);
  return privateAxios
    .post(
      `/user/${PostData.userId}/category/${PostData.categoryId}/posts`,
      PostData
    )
    .then((response) => response.data);
};

// Load all posts with pagination
export const LoadAllPost = (pageNumber, pageSize) => {
  return myAxios
    .get(
      `/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addedDate&sortDir=desc`
    )
    .then((response) => response.data);
};

// Load single post
export const loadParticularPost = (postid) => {
  return myAxios
    .get(`/posts/${postid}`)
    .then((response) => response.data);
};

// Add comment
export const commentOnParticular = (postid, comment) => {
  return privateAxios
    .post(`/post/${postid}/comments`, comment)
    .then((response) => response.data);
};

// Upload image
export const uploadImage = (image, postid) => {
  let formData = new FormData();
  formData.append("image", image);

  return privateAxios
    .post(`/post/image/upload/${postid}`, formData)
    .then((response) => response.data);
};

// Get posts by category
export const LoadPostByCategory = (catgoryId) => {
  return myAxios
    .get(`/category/${catgoryId}/posts`)
    .then((response) => response.data);
};

// Get user posts
export const usersPost = (id) => {
  return privateAxios
    .get(`/user/${id}/posts`)
    .then((response) => response.data);
};

// Delete post
export const deletePost = (postid) => {
  return privateAxios
    .delete(`/posts/${postid}`)
    .then((response) => response.data);
};

// Update post
export const updatePost = (post, postID) => {
  return privateAxios
    .put(`/posts/${postID}`, post)
    .then((response) => response.data);
};