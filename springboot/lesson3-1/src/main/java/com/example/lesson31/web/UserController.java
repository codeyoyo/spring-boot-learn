package com.example.lesson31.web;

import com.example.lesson31.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        List<User> r = new ArrayList<>(users.values());
        return r;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postUser(@PathVariable Long id, @ModelAttribute User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long id) {
        users.remove(id);
        return "success";
    }
}
