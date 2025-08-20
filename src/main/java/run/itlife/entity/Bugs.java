package run.itlife.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Bugs {

    @Id
    @Column(name="bug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bugId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name="bug_text")
    private String bugText;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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

    /**
     * Sets the value of the code property.
     *
     * @param bugText
     *     Текст бага. Возвращаемый тип
     *     {@link String }
     *
     */
    public void setBugText(String bugText) {
        this.bugText = bugText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the code property.
     *
     * @param createdAt
     *     Дата создания бага. Возвращаемый тип
     *     {@link LocalDateTime }
     *
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
