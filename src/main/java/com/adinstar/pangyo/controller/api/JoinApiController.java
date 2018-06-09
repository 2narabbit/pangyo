package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/star/join")
@MustLogin
public class JoinApiController {
    // checked!!! : 이미 가입한 사람이 또 join 하려고 하면.. 걍 error 뱉지말고 skip??? 그 반대도??

    @Autowired
    private StarService starService;

    @ApiOperation("joinedStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{starId}", method = RequestMethod.POST)
    public void joinedStar(@PathVariable("starId") long starId,
                           @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        starService.joinedStar(starId, viewerInfo.getId());
    }

    @ApiOperation("secededStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{starId}", method = RequestMethod.DELETE)
    public void secededStar(@PathVariable("starId") long starId,
                            @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
       starService.secededStar(starId, viewerInfo.getId());
    }
}
