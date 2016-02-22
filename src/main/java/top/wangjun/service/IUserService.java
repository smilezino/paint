package top.wangjun.service;

import top.wangjun.model.User;

import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 19:53
 */
public interface IUserService {
    public User add(User user);

    public User login(String usernameOrEmail, String password);

    public User login(Integer id, String password);

    public User admin();

    public User getBetween(Integer id, String username, String email);

    public List<User> getListBetween(Integer id, String username, String email);

    public int update(User user);
}
