package org.chen.spring.security5.boot.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户登录时携带的额外信息
 *
 * @author chensj
 * @date 2019-09-12 10:43
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {


    private static final long serialVersionUID = 8451867313411066177L;
    /**
     * 前台传入验证码
     */
    private final String verifyCode;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为页面中验证码的name
        verifyCode = request.getParameter("verifyCode");
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 将从表单中获取的验证码信息添加到参数中
        sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
        return sb.toString();
    }
}
