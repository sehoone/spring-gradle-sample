package com.sehoon.springgradlesample.common.mci.util;

import com.sehoon.springgradlesample.common.mci.constant.MciChannelConst;

public class DpMciUtil {
	public static <T> T mciCallSerivce(Object inVo, Class<T> outClass, String itrfId, String rcvSvcId, String inqrTraTypeCd) throws Exception {
        // 필수값 체크

        return send(inVo,outClass,itrfId,rcvSvcId,inqrTraTypeCd,"20220722");
	}

    private static <T> T send(Object inVo, Class<T> outClass, String itrfId, String rcvSvcId, String inqrTraTypeCd, String strYmd) throws Exception {
	
		// 헤더 vo 비즈니스별 셋팅
		
		return MciUtil.send(MciChannelConst.CHANNAL_A, inVo, outClass);
	}
}
