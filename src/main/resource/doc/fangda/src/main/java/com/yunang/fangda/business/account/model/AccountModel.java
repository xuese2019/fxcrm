package com.yunang.fangda.business.account.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
//以下jpa注解
@Entity(name = "account_table")
@Table(comment = "账户表", appliesTo = "account_table")

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountModel implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String uuid;

    @Column(name = "account", length = 32, unique = true, nullable = false)
    private String account;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    //    生成时间
    @Column(name = "system_times", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp systemTimes;

    //    是否允许登录
    @Column(name = "is_login", length = 1, nullable = false)
    private Integer isLogin;

    //    个人资料
    @OneToOne(targetEntity = UserModel.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private UserModel user;

    @Version
    private long version;

}
