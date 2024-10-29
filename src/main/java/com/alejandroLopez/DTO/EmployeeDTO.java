package com.alejandroLopez.DTO;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
    private String photoBase64; // Cambiamos a String para recibir Base64 desde el front
    private Long companyId;

    // Getters y setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhotoBase64() { return photoBase64; } // Getter para la imagen en Base64
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; } // Setter para la imagen

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
}
