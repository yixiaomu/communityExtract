package cn.passionshark.project.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    
    @Value("${community.key}")
    private String communityKey;

	public String getCommunityKey() {
		return communityKey;
	}

	public void setCommunityKey(String communityKey) {
		this.communityKey = communityKey;
	}


}
