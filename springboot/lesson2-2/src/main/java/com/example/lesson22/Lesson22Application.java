package com.example.lesson22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Lesson22Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Lesson22Application.class, args);

        Binder binder=Binder.get(context.getEnvironment());

        FooProperties fooProperties=binder.bind("com.lesson",Bindable.of(FooProperties.class)).get();
        System.out.println(fooProperties.getFoo());

        List<String> post=binder.bind("com.lesson.post",Bindable.listOf(String.class)).get();
        System.out.println(post);

        List<PostInfo> postInfoList=binder.bind("com.lesson.posts",Bindable.listOf(PostInfo.class)).get();
        System.out.println(postInfoList);

        System.out.println(context.getEnvironment().containsProperty("com.lesson.database-platform"));
        System.out.println(context.getEnvironment().containsProperty("com.lesson.databasePlatform"));
    }
}
