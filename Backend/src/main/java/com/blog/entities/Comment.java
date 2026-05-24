package com.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Comment content is required")
    @Column(nullable = false)
    private String content;

    // Many comments belong to one post
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

	public Comment(int id, @NotBlank(message = "Comment content is required") String content, Post post) {
		super();
		this.id = id;
		this.content = content;
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}