package com.yunang.fangda.business.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
//以下jpa注解
@Entity(name = "user_table")
@Table(comment = "个人资料表", appliesTo = "user_table")

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String uuid;

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "sex", length = 1)
    private Integer sex;

    @Column(name = "age", length = 3)
    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "birthday")
    private Date birthday;

    @Version
    private long version;

}
