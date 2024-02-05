package com.cos.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data // G,S
@Entity // JPA
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mysql autoincrement
    private long id;
    private String username;
    private String password;
    private String roles; //USER , ADMIN

    public List<String> getRoleList(){ // 롤이 하나의 유저당 두개 있으면 편한 방식, 애초에 롤이라는 모델(테이블)을 하나 더 만들어서 해도 된다.
        if (this.roles.length() > 0 ){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
