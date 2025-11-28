//package com.travmate.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String photoUrl;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    @JsonIgnore // hides password from JSON response
//    private String password;
//
//    private String name;
//    private String phone;
//    private String bio;
//    private String preferredCurrency = "INR";
//    private String preferredLanguage = "English";
//
//    @Column(nullable = false)
//    private String role = "ROLE_USER"; // default role
//
//    public User() {}
//
//    public User(String email, String password, String name, String role) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.role = role;
//    }
//
//    // --- Getters & Setters ---
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//
//    public String getBio() { return bio; }
//    public void setBio(String bio) { this.bio = bio; }
//
//    public String getPreferredCurrency() { return preferredCurrency; }
//    public void setPreferredCurrency(String preferredCurrency) { this.preferredCurrency = preferredCurrency; }
//
//    public String getPreferredLanguage() { return preferredLanguage; }
//    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }
//
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
//
//
//    @Column(name = "photo_url")
//
//    public String getPhotoUrl() { return photoUrl; }
//    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
//
//
//
//
//}



//after deployment
package com.travmate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore // never return password
    private String password;

    private String name;
    private String phone;
    private String bio;
    private String preferredCurrency = "INR";
    private String preferredLanguage = "English";

    @Column(nullable = false)
    private String role = "ROLE_USER";

    // --- Settings we added for Settings page ---
    @Column(nullable = false)
    private boolean emailNotifications = true;

    @Column(nullable = false)
    private boolean tripReminders = true;

    @Column(nullable = false)
    private boolean tripJoinRequests = true;

    @Column(nullable = false)
    private boolean publicProfile = true;

    @Column(nullable = false)
    private boolean allowTripRequests = true;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Column(name = "photo_url")
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPreferredCurrency() { return preferredCurrency; }
    public void setPreferredCurrency(String preferredCurrency) { this.preferredCurrency = preferredCurrency; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }

    public boolean isTripReminders() { return tripReminders; }
    public void setTripReminders(boolean tripReminders) { this.tripReminders = tripReminders; }

    public boolean isTripJoinRequests() { return tripJoinRequests; }
    public void setTripJoinRequests(boolean tripJoinRequests) { this.tripJoinRequests = tripJoinRequests; }

    public boolean isPublicProfile() { return publicProfile; }
    public void setPublicProfile(boolean publicProfile) { this.publicProfile = publicProfile; }

    public boolean isAllowTripRequests() { return allowTripRequests; }
    public void setAllowTripRequests(boolean allowTripRequests) { this.allowTripRequests = allowTripRequests; }
}

