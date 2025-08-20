package run.itlife.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@JsonAutoDetect
public class BugsDto {
    @JsonIgnore
    private Long bugId;
    private Long userId;
    private String bugText;
    @JsonIgnore
    private LocalDateTime createdAt;
    private String createdAtText;
    private String username;


    public String getCreatedAtText() {
        return createdAtText;
    }

    public void setCreatedAtText(String createdAtText) {
        this.createdAtText = createdAtText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public String getBugText() {
        return bugText;
    }

    public void setBugText(String bugText) {
        this.bugText = bugText;
    }

}
