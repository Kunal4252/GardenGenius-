package com.kunal.gardengenius.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kunal.gardengenius.entity.Post;
import com.kunal.gardengenius.entity.User;
import com.kunal.gardengenius.repository.PostRepository;
import com.kunal.gardengenius.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	// Method for searching posts by title
	public List<Post> searchPostsByTitle(String title) {
		// Assuming you have a repository method for searching posts by title
		return postRepository.searchByTitleIgnoringSpaces(title);
	}

	// Get all posts
	public List<Post> getAllPosts() {
		return postRepository.findAllOrderedByDateDesc();
	}

	public List<Post> getPostsByUserId(Long userId) {
		return postRepository.findByUserIdOrderedByDateDesc(userId);
	}

	public boolean deletePost(Long id) {
		if (postRepository.existsById(id)) {
			postRepository.deleteById(id);
			return true;
		}
		return false;
	}

	// Get a post by ID
	public Optional<Post> getPostById(Long id) {
		return postRepository.findById(id);
	}

	// Create a post
	public Post createPost(Post post) {
		Optional<User> user = userRepository.findById(post.getUser().getId());
		if (user.isPresent()) {
			post.setCreatedDate(LocalDateTime.now());
			post.setUser(user.get());
			return postRepository.save(post);
		} else {
			throw new IllegalArgumentException("User not found");
		}
	}
}
