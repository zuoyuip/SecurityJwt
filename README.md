基于SpringSecurity、JWT的权限认证系统
---------

> ## *如果您要测试——*
* #### 项目启动后自动建表(JPA)
* #### 注册（登录）的账户名称字段为`userName`
* #### 注册（登录）的账户密码字段为`passWord`
* #### 登录时的“记住我”字段为`rememberMe`

> ## *如何使用——*
> 
>1. 登录之后，在响应`Response`的头`Headers`里有字段`Authorization`，该值就是`Token`。
>
>2. 后续的访问需在`Request`的头`Headers`内携带字段`Authorization`和其值，以此来表示身份。
>
>3. `rememberMe`默认时效为一小时，为`true`时时效7天，设置路径在`org.zuoyu.security.constants.JwtConstants`。
>
>4. 测试路径查看`org.zuoyu.security.controller.AuthController`类。

-------

## 该项目无任何依赖和侵入，拿来即用，易自定义配置
