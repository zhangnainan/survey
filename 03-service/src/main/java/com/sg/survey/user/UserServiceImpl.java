package com.sg.survey.user;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Created by jiuge on 2020/6/23.
 */
@Service("survey.survey.user.service.impl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result login(UserModel userModel) {

        Result result = new Result();
        try{
            if(Validator.isEmpty(userModel) || Validator.isEmpty(userModel.getUsername()) || Validator.isEmpty(userModel.getPassword())){
                result.setMessage(Message.NotEmpty.getType());
                return result;
            }
            userModel.setPassword(DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes()));
            UserModel loginUserModel = userDao.login(userModel);

            if(!Validator.isEmpty(loginUserModel)){
                result.setData(loginUserModel);
                result.setMessage(Message.Success.getType());
            }else{
                result.setMessage(Message.NoExist.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
