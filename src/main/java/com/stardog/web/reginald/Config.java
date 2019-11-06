// Copyright (c) 2010 - 2019, Stardog Union. <http://stardog.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@stardog.com or visit http://stardog.com

package com.stardog.web.reginald;

/**
 * <p></p>
 *
 * @author Stephen Nowell
 */
public class Config {

	private static String BASE_LOCATION = "/stardog/reggie/%s/%s";

	private String mDeploymentName;
	private String mReggieLambdaUrl;

	public Config() {
		mDeploymentName = System.getenv("DEPLOY_NAME");
		mReggieLambdaUrl = String.format("https://reginald%sblue.stardog.com/lic", mDeploymentName);
	}

	public String getReggieLambdaUrl() {
		return mReggieLambdaUrl;
	}
}
