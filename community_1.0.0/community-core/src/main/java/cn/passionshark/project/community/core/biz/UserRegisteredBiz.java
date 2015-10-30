package cn.passionshark.project.community.core.biz;

import cn.passionshark.project.community.core.dto.RegisteredRequestDto;
import cn.passionshark.project.community.core.dto.RegisteredResponseDto;

public interface UserRegisteredBiz {

	RegisteredResponseDto registUser(RegisteredRequestDto request);
}
