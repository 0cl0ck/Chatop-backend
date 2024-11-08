package com.chatop.chatop.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Integer surface;
    private Integer price;
    private String picture;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getSurface() { return surface; }
    public void setSurface(Integer surface) { this.surface = surface; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
}