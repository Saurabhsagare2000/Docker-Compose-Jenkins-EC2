import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import "bootstrap/dist/css/bootstrap.min.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import About from './pages/About';
import Login from './pages/Login';
import Signup from './pages/Signup';
import UserDashboard from './pages/UserDashboard';
import ProfileInfo from './pages/ProfileInfo';
import NewFeed from './pages/NewsFeed';
import PostDetail from './pages/PostDetail';
import Categories from './pages/Categories';
import DeletePost from './pages/DeletePost';
import Update from './pages/Update';
import AddPost from './Component/AddPost';

import UserProvider from './Context/UserProvider';

function App() {
  return (
    <UserProvider>
      <BrowserRouter>

        <ToastContainer
          className="d-flex"
          style={{ marginRight: "40%" }}
        />

        <Routes>

          {/* Public Routes */}
          <Route path="/home" element={<NewFeed />} />
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />

          {/* User Routes */}
          <Route path="/user" element={<UserDashboard />}>
            <Route path="dashboard" element={<AddPost />} />
            <Route path="profile" element={<ProfileInfo />} />
            <Route path="posts/:id" element={<DeletePost />} />
            <Route path="update/:postID" element={<Update />} />
          </Route>

          {/* Other Routes */}
          <Route path="/postdetail/:postid" element={<PostDetail />} />
          <Route path="/category/:categoryId" element={<Categories />} />

        </Routes>

      </BrowserRouter>
    </UserProvider>
  );
}

export default App;