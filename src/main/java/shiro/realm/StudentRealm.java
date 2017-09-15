package shiro.realm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import dao.StudentDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pojo.Student;

import javax.annotation.Resource;
import java.util.Set;


@Component
public class StudentRealm extends AuthorizingRealm {

    @Resource
    private StudentDao StudentDao;

    //权限授予
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString() ;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
        Set<String> roleName = StudentDao.getRoles(username) ;
        Set<String> permissions = StudentDao.getPermissions(username) ;
        info.setRoles(roleName);
        info.setStringPermissions(permissions);
        return info;

    }

    //密码验证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username =  token.getPrincipal().toString();
        Student student = StudentDao.search(username);
        if (student != null){
            //将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断。第三个参数随便放一个就行了。
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(student.getUsername(),student.getPassword(),
                    this.getName()) ;
            return authenticationInfo ;
        }else{
            return  null ;
        }


    }

    //清除缓存,更新权限
    public void clearCached(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}
