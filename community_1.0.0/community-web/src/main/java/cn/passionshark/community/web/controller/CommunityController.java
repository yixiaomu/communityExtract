package cn.passionshark.community.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.passionshark.project.community.api.dto.common.ResponseDto;
import cn.passionshark.project.community.core.biz.UserRegisteredBiz;
import cn.passionshark.project.community.core.dto.RegisteredRequestDto;
import cn.passionshark.project.community.core.dto.RegisteredResponseDto;
import cn.passionshark.project.community.core.util.AbstractTradeFacade;

@Controller
@RequestMapping(value = "/api")
public class CommunityController extends AbstractTradeFacade {

    static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private UserRegisteredBiz userRegisteredBiz;
    
    /**
     * 注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/registered", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto<RegisteredResponseDto> rechargeConfirm(RegisteredRequestDto request) {
        return execute(request, new TradeCallback<RegisteredRequestDto, RegisteredResponseDto>() {
            @Override
            public RegisteredResponseDto doInTransaction(RegisteredRequestDto request) {
                return userRegisteredBiz.registUser(request);
            }
        });
    }
}
