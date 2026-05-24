package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;

    @NotBlank(message = "Post title is required")
    @Size(min = 5, max = 100)
    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @NotBlank(message = "Post content is required")
    @Column(name = "post_content", length = 1000000)
    private String content;

    @Column(name = "post_image_name")
    private String imageName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_added_date")
    private Date addedDate;

    // Many posts belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    // Many posts belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // One post can have many comments
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Post(Integer postId, @NotBlank(message = "Post title is required") @Size(min = 5, max = 100) String title,
			@NotBlank(message = "Post content is required") String content, String imageName, Date addedDate,
			Category category, User user, List<Comment> comments) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.imageName = imageName;
		this.addedDate = addedDate;
		this.category = category;
		this.user = user;
		this.comments = comments;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}