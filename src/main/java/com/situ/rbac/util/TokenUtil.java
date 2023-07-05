package com.situ.rbac.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Token生成与验证
 * @author bobzyh
 *
 */
@Component
public class TokenUtil {
	
	private static String privateKey = "J0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";	// 密钥
	private static long expire = 30*60*1000;	// 过期时间 毫秒 
	
	public static class Token{
		private String code;
		private long timestamp;
		private Integer status = 0;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
	}
	
	/**
	 * 生成Token
	 * @param code
	 * @return
	 */
	public static String createToken(String code) {
		
		Token token = new Token();
		token.setCode(code);
		token.setTimestamp(System.currentTimeMillis());
		
		String strToken = JWT.create()
				.withClaim("code", token.getCode())
				.withClaim("timestamp", token.getTimestamp())
				.sign(Algorithm.HMAC256(privateKey));
		
		return strToken;
	}
	
	/**
	 * 解析Token
	 * @param strToken
	 * @return
	 */
	public static Token parseToken(String strToken) {
		if (strToken == null || strToken.length() == 0) {
			return null;
		}
		
		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(privateKey))
				.build().verify(strToken);
		
		Claim code = decodedJWT.getClaim("code");
		Claim timestamp = decodedJWT.getClaim("timestamp");

		Token token = new Token();
		token.setCode(code.asString());
		token.setTimestamp(timestamp.asLong());
		
		// 检查过期
		if (token.getTimestamp() + expire < System.currentTimeMillis()) {
			token.setStatus(1);// 过期了
		}
		
		return token;
	}
}
