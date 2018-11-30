package com.example.lesson410;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;

@Entry(base = "ou=people,dc=lesson,dc=com",objectClasses = "inetOrgPerson")
@Data
public class Person {

    @Id
    private Name Id;
    @DnAttribute(value = "uid", index = 3)
    private String uid;
    @Attribute(name = "cn")
    private String commonName;
    @Attribute(name = "sn")
    private String suerName;
    private String userPassword;
}