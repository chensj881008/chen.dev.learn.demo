package org.chen.spring.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户登录时携带的额外信息
 *
 * @author chensj
 * @date 2019-09-13 下午11:10
 */
public class CustomAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 8189463302530766121L;
    /**
     * 前台输入的验证码数据
     */
    private final String verifyCode;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public CustomAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 从请求中获取验证码信息，verifyCode为页面中验证码的name
        verifyCode = request.getParameter("verifyCode");
    }

    /**
     * 验证码 获取方法
     * 可以提供其他类获取
     *
     * @return verifyCode
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
        return sb.toString();
    }
}
