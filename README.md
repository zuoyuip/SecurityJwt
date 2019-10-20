# SecurityJwt
> ### 一个基于springSecurity的Json Web Token的实现
>
> [GitHub地址](https://github.com/ZuoYuYouYi/SecurityJwt.git)

---------



## 提要

###  一、SpringSecurity

* `Spring Security`，一种基于` Spring AOP` 和 `Servlet `过滤器的安全框架。它提供全面的安全性解决方案，同时在 `Web` 请求级和方法调用级处理身份确认和授权。。
* 来自`Spring`全家桶系列，与`SpringBoot`无缝衔接。为应用系统提供声明式的安全访问控制功能，减少了为企业系统安全控制编写大量重复代码的工作。



### 二、JSON Web Token

* `JSON Web Token（JWT）`准确来说是一个规范。实际上就是一个字符串，它由三部分组成——头部**（`Header`）**、载荷**（`playload`）**与签名**（`signature`）**。
* **头部（`Header`）**用于描述关于该`JWT`的最基本的信息，即该`JWT`本身的信息声明，如签名所用算法。
* **载荷（`playload`）**是存放有效信息的地方。其中信息又分为三个部分——**声明部分**、**公共部分(`subject`)**、**私有部分(`claim`)**。
* **签证（`signature`）**需要`base64`加密后的**`header`**和`base64`加密后的**`payload`**使用.连接组成的字符串，然后通过**`header`**中声明的加密方式进行加盐`secret`组合加密构成（**注意**：`secret`是保存在服务器端的）。
* 在分布式中直接根据`token`取出保存的用户信息，以及对`token`可用性校验，单点登录更为简单。

* ![Image of Token](https://upload-images.jianshu.io/upload_images/3383598-c82676bb8445bae9.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)



## 三、开发环境介绍

* `Java`版本：1.8
* 构建工具：`Gradle`（目前国内主流构建工具依然是`Maven`，但是笔者用过`Gradle`之后就不想再用`Maven`了，因为`Gradle`是真的方便很多。其仓库结构向下兼容`Maven`，也就是说可以使用任何`Maven`仓库。

`build.gradle`文件：

````groovy
plugins {
    id 'org.springframework.boot' version '2.2.0.RELEASE'
    id 'io.spring.dependency-management' version '1.0.8.RELEASE'
    id 'java'
}

group = 'org.zuoyu'
version = '1.0.0'
sourceCompatibility = '1.8'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
//    这里使用的是阿里巴巴的Maven仓库
    maven {
        url 'http://maven.aliyun.com/nexus/content/groups/public/'
    }
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'mysql:mysql-connector-java'
    annotationProcessor 'org.projectlombok:lombok'
//    jwt依赖
    runtime('io.jsonwebtoken:jjwt-jackson:0.10.7')
    runtime('io.jsonwebtoken:jjwt-impl:0.10.7')
    compile('io.jsonwebtoken:jjwt-api:0.10.7')
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation 'org.springframework.security:spring-security-test'
}

test {
    useJUnitPlatform()
}

````



## 四、源码说明

> #### **在这里只分析关键代码**（其中的`JwtConstants.java`是我自定义的`final`变量类）
>
> ####  **备注：** 在`security`的配置文件中，将`session`管理器关闭，没有必要使用`session`。

#### 1. `JwtTokenUtils.java`（JWT的工具类）

````java
/**
   * 构建JWT
   *
   * @param subject - 实体
   * @param authorities - 权限
   * @param expiration - 保留时间
   * @return - token
   */
  private static String createJwt(String subject,
      String authorities, long expiration) {
    long nowMillis = System.currentTimeMillis();
    return Jwts.builder()
        .setId(JwtConstants.createTokenId())
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .setIssuer(JwtConstants.JWT_ISSUER)
        .setSubject(subject)
        .claim(JwtConstants.ROLE_CLAIMS, authorities)
        .setIssuedAt(new Date(nowMillis))
        .setNotBefore(new Date(nowMillis))
        .setExpiration(new Date(nowMillis + expiration * 1000L))
        .compact();
  }
````

在这里我们使用官方依赖包中的`Jwts.builder()`方法，创建一个`token`，其中——

* `signWith`就是设置私密钥与加密方式，`SECRET_KEY`为私密钥，`SignatureAlgorithm.HS256`为加密方式。
* `setSubject`为设置公共部分，该部分在客户端可解密。
* `claim`为设置私有部分，其参数为`key`—`value`形式。
* `setIssuedAt`为`token`的签发时间。
* `setNotBefore`为`token`的生效时间。
* `setExpiration`为`token`的失效时间。



**解析`token`：**

````java
/**
   * 解析token
   *
   * @param token -
   * @return - Claims
   */
  private static Claims parseJwt(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
````

在这里重点在与`setSigningKey`，传入我们在创建时候的私密钥`SECRET_KEY`。





**还有几个与`security`方便交互的方法：**

````java
 /**
   * 根据账户构建token
   *
   * @param user - 账户
   * @return -
   */
  public static String createToken(User user, boolean isRememberMe) {
    long expiration =
        isRememberMe ? JwtConstants.EXPIRATION_REMEMBER : JwtConstants.EXPIRATION;
    String spacer = ",";
    List<String> authorities = Arrays.stream(user.getRoles().split(spacer))
        .map(role -> "ROLE_" + role)
        .collect(Collectors.toList());
    return createJwt(JsonUtil.beanToJsonString(user), JsonUtil.objectToJsonString(authorities),
        expiration);
  }



  /**
   * 获取用户
   *
   * @param token - token
   * @return - User
   */
  public static User getUserByToken(String token) {
    String subject = parseJwt(token).getSubject();
    return JsonUtil.jsonStringToBean(subject, User.class);
  }


/**
   * 获取用户的权限
   * @param token - token
   * @return - 权限列表
   */
  public static Collection<? extends GrantedAuthority> getAuthoritiesByToken(String token) {
    String roles = parseJwt(token).get(JwtConstants.ROLE_CLAIMS).toString();
    return JsonUtil.jsonStringToCollection(roles, SimpleGrantedAuthority.class);
  }
````







#### 2. `AuthenticationSuccessHandlerImpl.java`（Security登录成功后的执行行为）

````java
/**
 * 登录成功的实现.
 *
 * @author zuoyu
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String rememberMe = request.getParameter(JwtConstants.USER_LOGIN_REMEMBER_ME);
    boolean isRememberMe = Boolean.parseBoolean(rememberMe);
    User principal = (User) authentication.getPrincipal();
    String token = JwtTokenUtils.createToken(principal, isRememberMe);
    response.setContentType("application/json;charset=utf-8");
    response.setHeader(JwtConstants.TOKEN_HEADER, token);
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter responseWriter = response.getWriter();
    responseWriter.write("{\"message\":\"登录成功\"}");
    responseWriter.flush();
    responseWriter.close();
  }


}

````

这段代码主要思路是——登录成功后，在`authentication`中获取已经认证成功的用户信息(`user`)，然后将该`user`转换为`token`并返回给客户端。其中的`isRememberMe`是根据是否为`true`给予`token`不同的有效时间（查看完整源代码）。





#### 3. `JwtAuthorizationFilter.java`（自定义基于JWT认证的过滤器）

````java
/**
 * JWT的权限过滤器.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-17 16:26
 **/
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String token = request.getHeader(JwtConstants.TOKEN_HEADER);
    if (StringUtils.isEmpty(token)) {
      chain.doFilter(request, response);
      return;
    }
    User user = JwtTokenUtils.getUserByToken(token);
    Collection<? extends GrantedAuthority> authorities = JwtTokenUtils.getAuthoritiesByToken(token);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        user, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    super.doFilterInternal(request, response, chain);
  }
}

````

这段代码的从请求中获取`token`，并将从`token`中解析出用户信息(`user`)和权限信息(`authorities`)。并根据用户信息(`user`)和权限信息(`authorities`)创建属于`security`框架的权限身份(`authentication`)，将其存入当前的`security`环境。





## 五、使用方法

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
>3. `rememberMe`默认时效为一小时，为`true`时时效7天，设置路径在`org.zuoyu.security.jwt.constants.JwtConstants.java`。
>
>4. 测试路径查看`org.zuoyu.security.jwt.controller.AuthController.java`类。
-------

## 该项目无任何依赖和侵入，拿来即用。