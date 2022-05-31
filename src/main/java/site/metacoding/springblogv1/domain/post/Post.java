package site.metacoding.springblogv1.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.springblogv1.domain.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 300, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)//n:1에서 n에 포린키를 줘야한다. user1명이 post여러개이므로, post가 n이다.
    private User user;

     @CreatedDate
     private LocalDateTime createDate;

@LastModifiedDate 
    private LocalDateTime updateDate;
    }
