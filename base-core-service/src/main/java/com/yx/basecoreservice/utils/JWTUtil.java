package com.yx.basecoreservice.utils;

import com.yx.basecoreservice.enums.EnumResponseCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/19
 * @time 1:33 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
public class JWTUtil {

    private static RSAPrivateKey priKey;
    private static RSAPublicKey pubKey;

    private static class SingletonHolder {
        private static final JWTUtil INSTANCE = new JWTUtil();
    }

    /**
     * 自定义加密key
     *
     * @param modulus
     * @param privateExponent
     * @param publicExponent
     * @return
     */
    public synchronized static JWTUtil getInstance(String modulus, String privateExponent, String publicExponent) {
        if (priKey == null && pubKey == null) {
            priKey = RSAUtil.getPrivateKey(modulus, privateExponent);
            pubKey = RSAUtil.getPublicKey(modulus, publicExponent);
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 重新加载加密key
     *
     * @param modulus
     * @param privateExponent
     * @param publicExponent
     */
    public synchronized static void reload(String modulus, String privateExponent, String publicExponent) {
        priKey = RSAUtil.getPrivateKey(modulus, privateExponent);
        pubKey = RSAUtil.getPublicKey(modulus, publicExponent);
    }

    /**
     * 默认加密key
     *
     * @return
     */
    public synchronized static JWTUtil getInstance() {
        if (priKey == null && pubKey == null) {
            priKey = RSAUtil.getPrivateKey(RSAUtil.modulus, RSAUtil.private_exponent);
            pubKey = RSAUtil.getPublicKey(RSAUtil.modulus, RSAUtil.public_exponent);
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取Token
     *
     * @param uid
     * @param exp 过期时间（单位：分钟）
     * @return
     */
    public String getToken(String uid, int exp) {
        long endTime = System.currentTimeMillis() + 1000L * 60L * exp;
        return Jwts.builder()
//                .setSubject(uid) //用户id
                .claim("uid", uid)
                .setExpiration(new Date(endTime))//过期时间
                .signWith(SignatureAlgorithm.RS512, priKey)//签名算法
                .compact();
    }

    /**
     * 获取Token（可保存登录信息）
     *
     * @param uid
     * @param loginInfo 不能含有逗号
     * @param exp       过期时间（单位：分钟）
     * @return
     */
    public String getToken(String uid, String loginInfo, int exp) {
        if (StringUtils.isBlank(loginInfo)) {
            loginInfo = "default";
        }
        long endTime = System.currentTimeMillis() + 1000L * 60L * exp;
        return Jwts.builder()
                // .setSubject(uid+"," + loginInfo)
                .claim("uid", uid)
                .claim("info", loginInfo)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey)
                .compact();
    }

    /**
     * 检查Token是否合法
     *
     * @param token
     * @return JWTResult
     */
    public JWTResult checkToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new JWTResult(false, null, null, "token已过期", EnumResponseCode.TOKEN_TIMEOUT_CODE.getCode());
        } catch (SignatureException e) {
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new JWTResult(false, null, null, "非法请求", EnumResponseCode.NO_AUTH_CODE.getCode());
        } catch (Exception e) {
            return new JWTResult(false, null, null, "非法请求", EnumResponseCode.NO_AUTH_CODE.getCode());
        }
        String uid = (String) claims.get("uid");
        String info = (String) claims.get("info");
        return new JWTResult(true, uid, info, "合法请求", EnumResponseCode.SUCCESS_CODE.getCode());
    }

    /**
     * 刷新token
     * 1.原token未过期刷新
     * 2.原token过期刷新 -- 不安全，不予支持
     *
     * @param oldToken
     * @param exp
     * @return
     */
    public String refreshToken(String oldToken, int exp) {
        Claims claims = null;
        try {
            //token校验
            claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(oldToken).getBody();
        }
        //不安全
//        catch (ExpiredJwtException e) {
//            //token过期时刷新token，需要获取原token中的信息
//            log.info("===>>过期token刷新,token:{}",oldToken);
//            claims = e.getClaims();
//        }
        catch (Exception e) {
            log.error("===>>刷新token异常,token:{}", oldToken);
            return oldToken;
        }
        String uid = (String) claims.get("uid");
        String info = (String) claims.get("info");
        return this.getToken(uid, info, exp);
    }

    /**
     * JWT信息封装类
     */
    public static class JWTResult {
        private boolean status;
        private String uid;//用户Id
        private String msg;
        private String loginInfo;//登录信息
        private int code;

        public JWTResult() {
            super();
        }

        public JWTResult(boolean status, String uid, String msg, int code) {
            super();
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
        }

        public JWTResult(boolean status, String uid, String loginInfo, String msg, int code) {
            super();
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
            this.loginInfo = loginInfo;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(String loginInfo) {
            this.loginInfo = loginInfo;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String oldeToken = JWTUtil.getInstance().getToken("1001", "heloo&qwert&sdfgh&123456", 30);
        System.out.println("===>>oldToken:" + oldeToken);
//        String neTOken = JWTUtil.getInstance().refreshToken(oldeToken, 6);
//        System.out.println("===>>neToken:" + neTOken);
    }
}
